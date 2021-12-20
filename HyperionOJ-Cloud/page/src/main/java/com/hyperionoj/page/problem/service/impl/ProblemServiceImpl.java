package com.hyperionoj.page.problem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.common.feign.OSSClients;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.CommentVo;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.SysUserVo;
import com.hyperionoj.common.vo.UpdateSubmitVo;
import com.hyperionoj.page.common.dao.mapper.CategoryMapper;
import com.hyperionoj.page.common.dao.mapper.TagMapper;
import com.hyperionoj.page.common.dao.pojo.PageCategory;
import com.hyperionoj.page.common.dao.pojo.PageTag;
import com.hyperionoj.page.common.vo.CategoryVo;
import com.hyperionoj.page.common.vo.TagVo;
import com.hyperionoj.page.common.vo.params.PageParams;
import com.hyperionoj.page.problem.dao.dos.ProblemArchives;
import com.hyperionoj.page.problem.dao.mapper.*;
import com.hyperionoj.page.problem.dao.pojo.*;
import com.hyperionoj.page.problem.service.ProblemService;
import com.hyperionoj.page.problem.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.hyperionoj.common.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
@Slf4j
@Service
public class ProblemServiceImpl implements ProblemService {

    private static final ConcurrentHashMap<String, RunResult> SUBMIT_RESULT = new ConcurrentHashMap<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm");
    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private ProblemBodyMapper problemBodyMapper;

    @Resource
    private CategoryMapper problemCategoryMapper;

    @Resource
    private ProblemCommentMapper problemCommentMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ProblemTagMapper problemTagMapper;

    @Resource
    private ProblemSubmitMapper problemSubmitMapper;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private RedisSever redisSever;

    @Resource
    private OSSClients ossClients;

    /**
     * 返回题目列表
     *
     * @param pageParams 分页参数
     * @return 题目
     */
    @Override
    public List<ProblemVo> getProblemList(PageParams pageParams) {
        Page<Problem> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Problem> problemPage = problemMapper.problemList(page, pageParams.getLevel(), Long.getLong(pageParams.getCategoryId()));
        return copyProblemList(problemPage.getRecords(), false, true);
    }

    /**
     * 通过题目id获取题目
     *
     * @param id 题目id
     * @return 题目
     */
    @Override
    public ProblemVo getProblemById(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            return null;
        }
        return problemToVo(problemMapper.selectById(id), true, true);
    }

    /**
     * 提交题目
     *
     * @param submitVo 用户提交数据
     * @return 本次提交情况
     */
    @Override
    public Object submit(SubmitVo submitVo) {
        SysUser sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        ProblemVo problemVo = problemToVo(problemMapper.selectById(submitVo.getProblemId()), false, false);
        submitVo.setCreateTime(dateFormat.format(System.currentTimeMillis()));
        submitVo.setCaseNumber(problemVo.getCaseNumber());
        submitVo.setRunTime(problemVo.getRunTime());
        submitVo.setRunMemory(problemVo.getRunMemory());
        if (!check(submitVo)) {
            RunResult runResult = new RunResult();
            runResult.setAuthorId(sysUser.getId().toString());
            runResult.setProblemId(Long.parseLong(submitVo.getProblemId()));
            runResult.setMsg("请不要使用系统命令或者非法字符");
            return runResult;
        }
        RunResult result = null;
        kafkaTemplate.send(KAFKA_TOPIC_SUBMIT, JSONObject.toJSONString(submitVo));
        try {
            long start = System.currentTimeMillis();
            while (null == (result = SUBMIT_RESULT.get(submitVo.getAuthorId() + UNDERLINE + submitVo.getProblemId()))) {
                if (System.currentTimeMillis() - start > 5000) {
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            log.warn(e.toString());
        }
        if (result != null) {
            ProblemSubmit problemSubmit = new ProblemSubmit();
            problemSubmit.setProblemId(result.getProblemId());
            problemSubmit.setAuthorId(sysUser.getId());
            problemSubmit.setUsername(sysUser.getUsername());
            problemSubmit.setCodeBody(submitVo.getCodeBody());
            problemSubmit.setRunMemory(result.getRunMemory());
            problemSubmit.setCodeLang(submitVo.getCodeLang());
            problemSubmit.setStatus(result.getVerdict());
            problemSubmit.setRunTime(result.getRunTime());
            try {
                problemSubmit.setCreateTime(dateFormat.parse(submitVo.getCreateTime()).getTime());
            } catch (ParseException e) {
                log.info(e.toString());
                problemSubmit.setCreateTime(System.currentTimeMillis());
            }
            problemSubmitMapper.insert(problemSubmit);
            UpdateSubmitVo updateSubmitVo = new UpdateSubmitVo();
            updateSubmitVo.setProblemId(problemSubmit.getProblemId());
            updateSubmitVo.setAuthorId(problemSubmit.getAuthorId());
            updateSubmitVo.setStatus(problemSubmit.getStatus());
            kafkaTemplate.send(KAFKA_TOPIC_SUBMIT_PAGE, JSONObject.toJSONString(updateSubmitVo));
            problemVo.setSubmitNumber(problemVo.getSubmitNumber() + 1);
            if (result.getVerdict().equals(AC)) {
                problemVo.setAcNumber(problemVo.getAcNumber() + 1);
            }
            this.updateProblemCache(problemVo);
            result.setSubmitTime(submitVo.getCreateTime());
            result.setSubmitId(problemSubmit.getId());
        }
        return result;
    }

    /**
     * 通过kafka接收消息
     *
     * @param record 运算结果
     */
    @KafkaListener(topics = {KAFKA_TOPIC_SUBMIT_RESULT}, groupId = "pageTest")
    public void kafkaResult(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            RunResult message = JSONObject.parseObject((String) kafkaMessage.get(), RunResult.class);
            SUBMIT_RESULT.put(message.getAuthorId() + UNDERLINE + message.getProblemId(), message);
        }
    }


    /**
     * 添加题目
     *
     * @param problemVo 题目对象
     * @return 新加的题目
     */
    @Override
    public ProblemVo addProblem(ProblemVo problemVo) {
        ProblemBodyVo problemBodyVo = problemVo.getProblemBodyVo();
        ProblemBody problemBody = voToProblemBody(problemBodyVo);
        problemBodyMapper.insert(problemBody);
        problemVo.setBodyId(problemBody.getId().toString());
        Problem problem = voToProblem(problemVo);
        problemMapper.insert(problem);
        problemVo.setId(problem.getId().toString());
        return problemVo;
    }

    /**
     * 修改题目
     *
     * @param problemVo 题目信息
     */
    @Override
    public void updateProblem(ProblemVo problemVo) {
        ProblemBodyVo problemBodyVo = problemVo.getProblemBodyVo();
        problemBodyVo.setId(problemVo.getBodyId());
        problemBodyMapper.updateById(voToProblemBody(problemBodyVo));
        problemMapper.updateById(voToProblem(problemVo));
        this.deleteProblemCache(problemVo);
    }

    private ProblemBody voToProblemBody(ProblemBodyVo problemBodyVo) {
        ProblemBody problemBody = new ProblemBody();
        if (null != problemBodyVo.getId()) {
            problemBody.setId(Long.valueOf(problemBodyVo.getId()));
        }
        problemBody.setProblemBody(problemBodyVo.getProblemBody());
        problemBody.setProblemBodyHtml(problemBodyVo.getProblemBodyHtml());
        return problemBody;
    }

    private Problem voToProblem(ProblemVo problemVo) {
        Problem problem = new Problem();
        if (null != problemVo.getId()) {
            problem.setId(Long.valueOf(problemVo.getId()));
        }
        problem.setTitle(problemVo.getTitle());
        problem.setBodyId(Long.valueOf(problemVo.getBodyId()));
        problem.setCategoryId(Long.valueOf(problemVo.getCategory().getId()));
        problem.setProblemLevel(problemVo.getProblemLevel());
        problem.setRunMemory(problemVo.getRunMemory());
        problem.setRunTime(problemVo.getRunTime());
        problem.setCaseNumber(problemVo.getCaseNumber());
        if (problemVo.getAcNumber() == null) {
            problem.setAcNumber(0);
        }
        if (problemVo.getSubmitNumber() == null) {
            problem.setSubmitNumber(0);
        }
        if (problemVo.getSolutionNumber() == null) {
            problem.setSolutionNumber(0);
        }
        if (problemVo.getCommentNumber() == null) {
            problem.setCommentNumber(0);
        }
        return problem;
    }

    /**
     * 删除题目
     *
     * @param problemVo 题目信息
     */
    @Override
    public void deleteProblem(ProblemVo problemVo) {
        problemBodyMapper.deleteById(problemVo.getBodyId());
        problemMapper.deleteById(problemVo.getId());
        this.deleteProblemCache(problemVo);
    }

    /**
     * 获取题目分类列表
     *
     * @return 题目所有类别
     */
    @Override
    public List<ProblemCategoryVo> getCategory() {
        LambdaQueryWrapper<PageCategory> queryWrapper = new LambdaQueryWrapper<>();
        List<PageCategory> problemCategories = problemCategoryMapper.selectList(queryWrapper);
        return problemCategoryToVoList(problemCategories);
    }

    private List<ProblemCategoryVo> problemCategoryToVoList(List<PageCategory> problemCategories) {
        ArrayList<ProblemCategoryVo> problemCategoryVos = new ArrayList<>();
        for (PageCategory category : problemCategories) {
            problemCategoryVos.add(problemCategoryToVo(category));
        }
        return problemCategoryVos;
    }

    private ProblemCategoryVo problemCategoryToVo(PageCategory category) {
        ProblemCategoryVo problemCategoryVo = new ProblemCategoryVo();
        problemCategoryVo.setId(category.getId().toString());
        problemCategoryVo.setCategoryName(category.getCategoryName());
        problemCategoryVo.setDescription(category.getDescription());
        return problemCategoryVo;
    }

    /**
     * 对题目进行评论
     *
     * @param commentVo 用户提交评论
     * @return 本次提交情况
     */
    @Override
    public CommentVo comment(CommentVo commentVo) {
        ProblemComment comment = voToComment(commentVo);
        problemCommentMapper.insert(comment);
        commentVo.setId(comment.getId().toString());
        commentVo.setCreateDate(dateFormat.format(comment.getCreateTime()));
        String problemVoRedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(commentVo.getProblemId());
        String redisKV = redisSever.getRedisKV(problemVoRedisKey);
        if (redisKV != null) {
            ProblemVo problemVo = JSONObject.parseObject(JSONObject.toJSONString(JSONObject.parseObject(redisKV, Result.class).getData()), ProblemVo.class);
            problemVo.setCommentNumber(problemVo.getCommentNumber() + 1);
            redisSever.setRedisKV(problemVoRedisKey, JSONObject.toJSONString(Result.success(problemVo)));
        }
        return commentVo;
    }

    /**
     * 删除评论
     *
     * @param commentVo 评论参数
     */
    @Override
    public void deleteComment(CommentVo commentVo) {
        LambdaUpdateWrapper<ProblemComment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ProblemComment::getId, commentVo.getId());
        updateWrapper.set(ProblemComment::getIsDelete, 1);
        problemCommentMapper.update(null, updateWrapper);
        String problemVoRedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(commentVo.getProblemId());
        ProblemVo problemVo = JSONObject.parseObject((String) JSONObject.parseObject(redisSever.getRedisKV(problemVoRedisKey), Result.class).getData(), ProblemVo.class);
        problemVo.setCommentNumber(problemVo.getCommentNumber() - 1);
        redisSever.setRedisKV(problemVoRedisKey, JSONObject.toJSONString(Result.success(problemVo)));
    }

    /**
     * 获取评论列表
     *
     * @param pageParams 分页参数
     * @return 评论列表
     */
    @Override
    public List<CommentVo> getCommentList(PageParams pageParams) {
        Page<ProblemComment> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<ProblemComment> commentPage = problemCommentMapper.getCommentList(page, pageParams.getProblemId());
        return problemCommitToVoList(commentPage.getRecords());
    }


    private List<CommentVo> problemCommitToVoList(List<ProblemComment> comments) {
        ArrayList<CommentVo> commentVos = new ArrayList<>();
        for (ProblemComment comment : comments) {
            commentVos.add(commentToVo(comment));
        }
        return commentVos;
    }

    private CommentVo commentToVo(ProblemComment comment) {
        CommentVo commentVo = new CommentVo();
        commentVo.setProblemId(comment.getProblemId().toString());
        commentVo.setAuthorVo(SysUserVo.userToVo(ossClients.findUserById(comment.getAuthorId().toString()).getData()));
        commentVo.setContent(comment.getContent());
        commentVo.setLevel(comment.getLevel());
        commentVo.setId(comment.getId().toString());
        commentVo.setParentId(comment.getParentId().toString());
        if (comment.getToUid() != null || comment.getToUid() != 0) {
            commentVo.setToUser(SysUserVo.userToVo(ossClients.findUserById(comment.getToUid().toString()).getData()));
        }
        commentVo.setSupportNumber(comment.getSupportNumber());
        commentVo.setCreateDate(dateFormat.format(comment.getCreateTime()));
        return commentVo;
    }

    private ProblemComment voToComment(CommentVo commentVo) {
        ProblemComment problemComment = new ProblemComment();
        problemComment.setProblemId(Long.parseLong(commentVo.getProblemId()));
        problemComment.setContent(commentVo.getContent());
        problemComment.setAuthorId(Long.parseLong(commentVo.getAuthorVo().getId()));
        problemComment.setIsDelete(0);
        problemComment.setSupportNumber(0);
        problemComment.setCreateTime(System.currentTimeMillis());
        problemComment.setLevel(commentVo.getLevel());
        problemComment.setParentId(Long.getLong(commentVo.getParentId()));
        problemComment.setToUid(Long.getLong(commentVo.getToUser().getId()));
        if (problemComment.getLevel() == null) {
            problemComment.setLevel(0);
        }
        if (problemComment.getParentId() == null) {
            problemComment.setParentId(0L);
        }
        if (problemComment.getToUid() == null) {
            problemComment.setToUid(0L);
        }
        return problemComment;
    }

    /**
     * 获取提交列表
     *
     * @param pageParams 分页查询参数
     * @return 根据分页参数返回简要提交信息
     */
    @Override
    public List<SubmitVo> getSubmitList(PageParams pageParams) {
        Page<ProblemSubmit> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<ProblemSubmit> submitList = problemSubmitMapper.getSubmitList(page,
                pageParams.getProblemId(),
                pageParams.getCodeLang(),
                pageParams.getUsername(),
                pageParams.getVerdict());
        return submitToVoList(submitList.getRecords());
    }

    /**
     * 获取提交详情
     *
     * @param id 提交id
     * @return 提交结果
     */
    @Override
    public SubmitVo getSubmitById(Long id) {
        LambdaQueryWrapper<ProblemSubmit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProblemSubmit::getId, id);
        return submitToVo(problemSubmitMapper.selectOne(queryWrapper), true);
    }

    /**
     * 题目下该评论的点赞数
     *
     * @param commentVo 评论参数
     * @return 目前得赞数
     */
    @Override
    public Integer support(CommentVo commentVo) {
        return problemCommentMapper.support(Long.valueOf(commentVo.getId()));
    }

    private List<SubmitVo> submitToVoList(List<ProblemSubmit> submits) {
        ArrayList<SubmitVo> submitVos = new ArrayList<>();
        for (ProblemSubmit submit : submits) {
            submitVos.add(submitToVo(submit, false));
        }
        return submitVos;
    }

    private SubmitVo submitToVo(ProblemSubmit submit, boolean isBody) {
        SubmitVo submitVo = new SubmitVo();
        submitVo.setId(submit.getId().toString());
        submitVo.setProblemId(submit.getProblemId().toString());
        submitVo.setAuthorId(submit.getAuthorId().toString());
        submitVo.setCodeLang(submit.getCodeLang());
        submitVo.setCreateTime(dateFormat.format(submit.getCreateTime()));
        submitVo.setRunTime(submit.getRunTime());
        submitVo.setRunMemory(submit.getRunMemory());
        submitVo.setVerdict(submit.getStatus());
        if (isBody) {
            submitVo.setCodeBody(submit.getCodeBody());
        }
        return submitVo;
    }

    /**
     * 添加题目分类
     *
     * @param problemCategoryVo 分类信息
     * @return 分类情况
     */
    @Override
    public ProblemCategoryVo addCategory(ProblemCategoryVo problemCategoryVo) {
        PageCategory category = new PageCategory();
        BeanUtils.copyProperties(problemCategoryVo, category);
        problemCategoryMapper.insert(category);
        problemCategoryVo.setId(category.getId().toString());
        return problemCategoryVo;
    }

    /**
     * 删除题目分类
     *
     * @param problemCategoryVo 分类参数
     */
    @Override
    public void deleteCategory(ProblemCategoryVo problemCategoryVo) {
        problemCategoryMapper.deleteById(problemCategoryVo.getId());
    }

    /**
     * 检查用户提交参数
     *
     * @param submitVo 用户提交数据
     * @return 是否通过检验
     */
    private boolean check(SubmitVo submitVo) {
        SysUser sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        if (sysUser.getId().equals(Long.getLong(submitVo.getAuthorId()))) {
            return false;
        }
        return !StringUtils.contains(submitVo.getCodeBody(), ILLEGAL_CHAR_SYSTEM);
    }

    private List<ProblemVo> copyProblemList(List<Problem> problemPage, boolean isBody, boolean isTag) {
        List<ProblemVo> problemVoList = new ArrayList<>();
        for (Problem problem : problemPage) {
            problemVoList.add(problemToVo(problem, isBody, isTag));
        }
        return problemVoList;
    }

    private ProblemVo problemToVo(Problem problem, boolean isBody, boolean isTag) {
        ProblemVo problemVo = new ProblemVo();
        problemVo.setId(problem.getId().toString());
        problemVo.setTitle(problem.getTitle());
        problemVo.setBodyId(problem.getBodyId().toString());
        problemVo.setProblemLevel(problem.getProblemLevel());
        PageCategory pageCategory = problemCategoryMapper.selectById(problem.getCategoryId());
        problemVo.setCategory(categoryToVo(pageCategory));
        problemVo.setAcNumber(problem.getAcNumber());
        problemVo.setSubmitNumber(problem.getSubmitNumber());
        problemVo.setSolutionNumber(problem.getSolutionNumber());
        problemVo.setCommentNumber(problem.getCommentNumber());
        problemVo.setRunTime(problem.getRunTime());
        problemVo.setRunMemory(problem.getRunMemory());
        problemVo.setCaseNumber(problem.getCaseNumber());
        if (isBody) {
            problemVo.setProblemBodyVo(problemBodyToVo(problemBodyMapper.selectById(problem.getBodyId())));
        }
        if (isTag) {
            ArrayList<TagVo> tagVos = new ArrayList<>();
            LambdaQueryWrapper<ProblemProblemTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProblemProblemTag::getProblemId, problem.getId());
            List<ProblemProblemTag> problemProblemTags = problemTagMapper.selectList(queryWrapper);
            for (ProblemProblemTag problemTag : problemProblemTags) {
                tagVos.add(tagToVo(tagMapper.selectById(problemTag.getTagId())));
            }
            problemVo.setTags(tagVos);
        }
        return problemVo;
    }

    private TagVo tagToVo(PageTag tag) {
        TagVo tagVo = new TagVo();
        tagVo.setId(tag.getId().toString());
        tagVo.setTagName(tag.getTagName());
        return tagVo;
    }

    private CategoryVo categoryToVo(PageCategory pageCategory) {
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setId(pageCategory.getId().toString());
        categoryVo.setCategoryName(pageCategory.getCategoryName());
        categoryVo.setDescription(pageCategory.getDescription());
        return categoryVo;
    }

    private ProblemBodyVo problemBodyToVo(ProblemBody problemBody) {
        if (ObjectUtils.isEmpty(problemBody)) {
            return null;
        }
        ProblemBodyVo problemBodyVo = new ProblemBodyVo();
        problemBodyVo.setProblemBodyHtml(problemBody.getProblemBodyHtml());
        return problemBodyVo;
    }

    /**
     * 更新问题的缓存
     *
     * @param problemVo 新的问题的所有参数
     */
    @Override
    public void updateProblemCache(ProblemVo problemVo) {
        String problemVoRedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(problemVo.getId());
        redisSever.setRedisKV(problemVoRedisKey, JSONObject.toJSONString(Result.success(problemVo)));
    }

    /**
     * 获取每天过题数量
     *
     * @return 数量列表
     */
    @Override
    public List<ProblemArchives> getEveryday() {
        SysUser sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        return problemSubmitMapper.getEveryday(sysUser.getId());
    }

    /**
     * 删除问题的缓存
     *
     * @param problemVo 问题参数
     */
    private void deleteProblemCache(ProblemVo problemVo) {
        String problemVoRedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(problemVo.getId());
        redisSever.delKey(problemVoRedisKey);
    }


}
