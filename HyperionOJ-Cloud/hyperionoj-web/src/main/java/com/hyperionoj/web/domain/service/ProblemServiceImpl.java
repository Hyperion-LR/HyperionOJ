package com.hyperionoj.web.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.web.application.api.ProblemService;
import com.hyperionoj.web.presentation.dto.PageParams;
import com.hyperionoj.web.presentation.vo.Result;
import com.hyperionoj.web.infrastructure.mapper.*;
import com.hyperionoj.web.infrastructure.po.*;
import com.hyperionoj.web.infrastructure.utils.RedisUtils;
import com.hyperionoj.web.infrastructure.utils.ThreadLocalUtils;
import com.hyperionoj.judge.vo.RunResult;
import com.hyperionoj.web.presentation.vo.*;
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

import static com.hyperionoj.web.infrastructure.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
@Slf4j
@Service
public class ProblemServiceImpl implements ProblemService {

    private static final ConcurrentHashMap<String, RunResult> SUBMIT_RESULT = new ConcurrentHashMap<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    @Resource
    private ProblemMapper problemMapper;

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
    private RedisUtils redisSever;

    @Resource
    private UserMapper userMapper;

    /**
     * 返回题目列表
     *
     * @param pageParams 分页参数
     * @return 题目
     */
    @Override
    public List<ProblemVO> getProblemList(PageParams pageParams) {
        Page<ProblemPO> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<ProblemPO> problemPage = problemMapper.problemList(page, pageParams.getLevel(), Long.getLong(pageParams.getCategoryId()));
        return copyProblemList(problemPage.getRecords(), false, true);
    }

    /**
     * 通过题目id获取题目
     *
     * @param id 题目id
     * @return 题目
     */
    @Override
    public ProblemVO getProblemById(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            return null;
        }
        return problemToVO(problemMapper.selectById(id), true, true);
    }

    /**
     * 提交题目
     *
     * @param submitVO 用户提交数据
     * @return 本次提交情况
     */
    @Override
    public Object submit(SubmitVO submitVO) {
        UserPO sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), UserPO.class);
        ProblemPO problem = problemMapper.selectById(submitVO.getProblemId());
        submitVO.setRunTime(problem.getRunTime());
        submitVO.setRunMemory(problem.getRunMemory());
        submitVO.setCaseNumber(problem.getCaseNumber());
        ProblemVO problemVO = problemToVO(problemMapper.selectById(submitVO.getProblemId()), false, false);
        submitVO.setCreateTime(dateFormat.format(System.currentTimeMillis()));
        submitVO.setCaseNumber(problemVO.getCaseNumber());
        submitVO.setRunTime(problemVO.getRunTime());
        submitVO.setRunMemory(problemVO.getRunMemory());
        if (!check(submitVO)) {
            RunResult runResult = new RunResult();
            runResult.setAuthorId(sysUser.getId().toString());
            runResult.setProblemId(Long.parseLong(submitVO.getProblemId()));
            runResult.setMsg("请不要使用系统命令或者非法字符");
            return runResult;
        }
        RunResult result = null;
        kafkaTemplate.send(KAFKA_TOPIC_SUBMIT, JSONObject.toJSONString(submitVO));
        try {
            long start = System.currentTimeMillis();
            while (null == (result = SUBMIT_RESULT.get(submitVO.getAuthorId() + UNDERLINE + submitVO.getProblemId()))) {
                if (System.currentTimeMillis() - start > 5000) {
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            log.warn(e.toString());
        }
        if (result != null) {
            ProblemSubmitPO problemSubmit = new ProblemSubmitPO();
            problemSubmit.setProblemId(result.getProblemId());
            problemSubmit.setAuthorId(sysUser.getId());
            problemSubmit.setUsername(sysUser.getUsername());
            problemSubmit.setCodeBody(submitVO.getCodeBody());
            problemSubmit.setRunMemory(result.getRunMemory());
            problemSubmit.setCodeLang(submitVO.getCodeLang());
            problemSubmit.setStatus(result.getVerdict());
            problemSubmit.setRunTime(result.getRunTime());
            try {
                problemSubmit.setCreateTime(dateFormat.parse(submitVO.getCreateTime()).getTime());
            } catch (ParseException e) {
                log.info(e.toString());
                problemSubmit.setCreateTime(System.currentTimeMillis());
            }
            problemSubmitMapper.insert(problemSubmit);
            UpdateSubmitVO updateSubmitVO = new UpdateSubmitVO();
            updateSubmitVO.setProblemId(problemSubmit.getProblemId());
            updateSubmitVO.setAuthorId(problemSubmit.getAuthorId());
            updateSubmitVO.setStatus(problemSubmit.getStatus());
            kafkaTemplate.send(KAFKA_TOPIC_SUBMIT_PAGE, JSONObject.toJSONString(updateSubmitVO));
            problemVO.setSubmitNumber(problemVO.getSubmitNumber() + 1);
            if (result.getVerdict().equals(AC)) {
                problemVO.setAcNumber(problemVO.getAcNumber() + 1);
                // this.updateEverydayCache(JSONObject.toJSONString(sysUser.getId()));
            }
            this.updateProblemCache(problemVO);
            result.setSubmitTime(submitVO.getCreateTime());
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
     * @param problemVO 题目对象
     * @return 新加的题目
     */
    @Override
    public ProblemVO addProblem(ProblemVO problemVO) {
        ProblemPO problem = voToProblem(problemVO);
        problemMapper.insert(problem);
        problemVO.setId(problem.getId().toString());
        return problemVO;
    }

    /**
     * 修改题目
     *
     * @param problemVO 题目信息
     */
    @Override
    public void updateProblem(ProblemVO problemVO) {
        problemMapper.updateById(voToProblem(problemVO));
        this.deleteProblemCache(problemVO);
    }

    private ProblemPO voToProblem(ProblemVO problemVO) {
        ProblemPO problem = new ProblemPO();
        if (null != problemVO.getId()) {
            problem.setId(Long.valueOf(problemVO.getId()));
        }
        problem.setTitle(problemVO.getTitle());
        problem.setCategoryId(Long.valueOf(problemVO.getCategory().getId()));
        problem.setProblemLevel(problemVO.getProblemLevel());
        problem.setRunMemory(problemVO.getRunMemory());
        problem.setRunTime(problemVO.getRunTime());
        if (problemVO.getAcNumber() == null) {
            problem.setAcNumber(0);
        } else {
            problem.setAcNumber(problemVO.getAcNumber());
        }
        if (problemVO.getSubmitNumber() == null) {
            problem.setSubmitNumber(0);
        } else {
            problem.setSubmitNumber(problemVO.getSubmitNumber());
        }
        if (problemVO.getSolutionNumber() == null) {
            problem.setSolutionNumber(0);
        } else {
            problem.setSolutionNumber(problemVO.getSolutionNumber());
        }
        if (problemVO.getCommentNumber() == null) {
            problem.setCommentNumber(0);
        } else {
            problem.setCommentNumber(problemVO.getCommentNumber());
        }
        return problem;
    }

    /**
     * 删除题目
     *
     * @param problemVO 题目信息
     */
    @Override
    public void deleteProblem(ProblemVO problemVO) {
        LambdaQueryWrapper<ProblemPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProblemPO::getId, problemVO.getId());
        queryWrapper.last("limit 1");
        problemMapper.deleteById(problemVO.getId());
        this.deleteProblemCache(problemVO);
    }

    /**
     * 获取题目分类列表
     *
     * @return 题目所有类别
     */
    @Override
    public List<CategoryVO> getCategory() {
        LambdaQueryWrapper<PageCategoryPO> queryWrapper = new LambdaQueryWrapper<>();
        List<PageCategoryPO> problemCategories = problemCategoryMapper.selectList(queryWrapper);
        return problemCategoryToVOList(problemCategories);
    }

    private List<CategoryVO> problemCategoryToVOList(List<PageCategoryPO> problemCategories) {
        ArrayList<CategoryVO> problemCategoryVOs = new ArrayList<>();
        for (PageCategoryPO category : problemCategories) {
            problemCategoryVOs.add(problemCategoryToVO(category));
        }
        return problemCategoryVOs;
    }

    private CategoryVO problemCategoryToVO(PageCategoryPO category) {
        CategoryVO problemCategoryVO = new CategoryVO();
        problemCategoryVO.setId(category.getId().toString());
        problemCategoryVO.setCategoryName(category.getCategoryName());
        problemCategoryVO.setDescription(category.getDescription());
        return problemCategoryVO;
    }

    /**
     * 对题目进行评论
     *
     * @param commentVO 用户提交评论
     * @return 本次提交情况
     */
    @Override
    public CommentVO comment(CommentVO commentVO) {
        ProblemCommentPO comment = voToComment(commentVO);
        problemCommentMapper.insert(comment);
        commentVO.setId(comment.getId().toString());
        commentVO.setCreateDate(dateFormat.format(comment.getCreateTime()));
        String problemVORedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(commentVO.getProblemId());
        String redisKV = redisSever.getRedisKV(problemVORedisKey);
        if (redisKV != null) {
            ProblemVO problemVO = JSONObject.parseObject(JSONObject.toJSONString(JSONObject.parseObject(redisKV, Result.class).getData()), ProblemVO.class);
            problemVO.setCommentNumber(problemVO.getCommentNumber() + 1);
            redisSever.setRedisKV(problemVORedisKey, JSONObject.toJSONString(Result.success(problemVO)));
        }
        return commentVO;
    }

    /**
     * 删除评论
     *
     * @param commentVO 评论参数
     */
    @Override
    public void deleteComment(CommentVO commentVO) {
        LambdaUpdateWrapper<ProblemCommentPO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ProblemCommentPO::getId, commentVO.getId());
        updateWrapper.set(ProblemCommentPO::getIsDelete, 1);
        problemCommentMapper.update(null, updateWrapper);
        LambdaQueryWrapper<ProblemCommentPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProblemCommentPO::getId, commentVO.getId());
        queryWrapper.select(ProblemCommentPO::getProblemId);
        ProblemCommentPO problemComment = problemCommentMapper.selectOne(queryWrapper);
        if (problemComment == null) {
            return;
        }
        queryWrapper.last("limit 1");
        String problemVORedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(problemComment.getProblemId().toString());
        String redisKV = redisSever.getRedisKV(problemVORedisKey);
        if (!StringUtils.isBlank(redisKV)) {
            ProblemVO problemVO = JSONObject.parseObject((String) JSONObject.parseObject(redisKV, Result.class).getData(), ProblemVO.class);
            problemVO.setCommentNumber(problemVO.getCommentNumber() - 1);
            redisSever.setRedisKV(problemVORedisKey, JSONObject.toJSONString(Result.success(problemVO)));
        }

    }

    /**
     * 获取评论列表
     *
     * @param pageParams 分页参数
     * @return 评论列表
     */
    @Override
    public List<CommentVO> getCommentList(PageParams pageParams) {
        Page<ProblemCommentPO> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<ProblemCommentPO> commentPage = problemCommentMapper.getCommentList(page, pageParams.getProblemId());
        return problemCommitToVOList(commentPage.getRecords());
    }


    private List<CommentVO> problemCommitToVOList(List<ProblemCommentPO> comments) {
        ArrayList<CommentVO> commentVOs = new ArrayList<>();
        for (ProblemCommentPO comment : comments) {
            commentVOs.add(commentToVO(comment));
        }
        return commentVOs;
    }

    private CommentVO commentToVO(ProblemCommentPO comment) {
        CommentVO commentVO = new CommentVO();
        commentVO.setProblemId(comment.getProblemId().toString());
        commentVO.setAuthorVo(UserVO.userToVo(userMapper.selectById(comment.getAuthorId().toString())));
        commentVO.setContent(comment.getContent());
        commentVO.setLevel(comment.getLevel());
        commentVO.setId(comment.getId().toString());
        commentVO.setParentId(comment.getParentId().toString());
        if (comment.getToUid() != null || comment.getToUid() != 0) {
            commentVO.setToUser(UserVO.userToVo(userMapper.selectById(comment.getToUid().toString())));
        }
        commentVO.setSupportNumber(comment.getSupportNumber());
        commentVO.setCreateDate(dateFormat.format(comment.getCreateTime()));
        return commentVO;
    }

    private ProblemCommentPO voToComment(CommentVO commentVO) {
        ProblemCommentPO problemComment = new ProblemCommentPO();
        problemComment.setProblemId(Long.parseLong(commentVO.getProblemId()));
        problemComment.setContent(commentVO.getContent());
        problemComment.setAuthorId(Long.parseLong(commentVO.getAuthorVo().getId()));
        problemComment.setIsDelete(0);
        problemComment.setSupportNumber(0);
        problemComment.setCreateTime(System.currentTimeMillis());
        problemComment.setLevel(commentVO.getLevel());
        problemComment.setParentId(Long.getLong(commentVO.getParentId()));
        if (commentVO.getToUser() != null) {
            problemComment.setToUid(Long.getLong(commentVO.getToUser().getId()));
        }
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
    public List<SubmitVO> getSubmitList(PageParams pageParams) {
        Page<ProblemSubmitPO> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<ProblemSubmitPO> submitList = problemSubmitMapper.getSubmitList(page,
                pageParams.getProblemId(),
                pageParams.getCodeLang(),
                pageParams.getUsername(),
                pageParams.getVerdict());
        return submitToVOList(submitList.getRecords());
    }

    /**
     * 获取提交详情
     *
     * @param id 提交id
     * @return 提交结果
     */
    @Override
    public SubmitVO getSubmitById(Long id) {
        LambdaQueryWrapper<ProblemSubmitPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProblemSubmitPO::getId, id);
        return submitToVO(problemSubmitMapper.selectOne(queryWrapper), true);
    }

    /**
     * 题目下该评论的点赞数
     *
     * @param commentVO 评论参数
     * @return 目前得赞数
     */
    @Override
    public Integer support(CommentVO commentVO) {
        return problemCommentMapper.support(Long.valueOf(commentVO.getId()));
    }

    private List<SubmitVO> submitToVOList(List<ProblemSubmitPO> submits) {
        ArrayList<SubmitVO> submitVOs = new ArrayList<>();
        for (ProblemSubmitPO submit : submits) {
            submitVOs.add(submitToVO(submit, false));
        }
        return submitVOs;
    }

    private SubmitVO submitToVO(ProblemSubmitPO submit, boolean isBody) {
        SubmitVO submitVO = new SubmitVO();
        submitVO.setId(submit.getId().toString());
        submitVO.setProblemId(submit.getProblemId().toString());
        submitVO.setAuthorId(submit.getAuthorId().toString());
        submitVO.setCodeLang(submit.getCodeLang());
        submitVO.setCreateTime(dateFormat.format(submit.getCreateTime()));
        submitVO.setRunTime(submit.getRunTime());
        submitVO.setRunMemory(submit.getRunMemory());
        submitVO.setVerdict(submit.getStatus());
        if (isBody) {
            submitVO.setCodeBody(submit.getCodeBody());
        }
        return submitVO;
    }

    /**
     * 添加题目分类
     *
     * @param problemCategoryVO 分类信息
     * @return 分类情况
     */
    @Override
    public CategoryVO addCategory(CategoryVO problemCategoryVO) {
        PageCategoryPO category = new PageCategoryPO();
        BeanUtils.copyProperties(problemCategoryVO, category);
        problemCategoryMapper.insert(category);
        problemCategoryVO.setId(category.getId().toString());
        return problemCategoryVO;
    }

    /**
     * 删除题目分类
     *
     * @param problemCategoryVO 分类参数
     */
    @Override
    public void deleteCategory(CategoryVO problemCategoryVO) {
        problemCategoryMapper.deleteById(problemCategoryVO.getId());
    }

    /**
     * 检查用户提交参数
     *
     * @param submitVO 用户提交数据
     * @return 是否通过检验
     */
    private boolean check(SubmitVO submitVO) {
        UserPO sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), UserPO.class);
        if (sysUser.getId().equals(Long.getLong(submitVO.getAuthorId()))) {
            return false;
        }
        return !StringUtils.contains(submitVO.getCodeBody(), ILLEGAL_CHAR_SYSTEM);
    }

    private List<ProblemVO> copyProblemList(List<ProblemPO> problemPage, boolean isBody, boolean isTag) {
        List<ProblemVO> problemVOList = new ArrayList<>();
        for (ProblemPO problem : problemPage) {
            problemVOList.add(problemToVO(problem, isBody, isTag));
        }
        return problemVOList;
    }

    private ProblemVO problemToVO(ProblemPO problem, boolean isBody, boolean isTag) {
        ProblemVO problemVO = new ProblemVO();
        problemVO.setId(problem.getId().toString());
        problemVO.setTitle(problem.getTitle());
        problemVO.setProblemLevel(problem.getProblemLevel());
        PageCategoryPO pageCategory = problemCategoryMapper.selectById(problem.getCategoryId());
        problemVO.setCategory(categoryToVO(pageCategory));
        problemVO.setAcNumber(problem.getAcNumber());
        problemVO.setSubmitNumber(problem.getSubmitNumber());
        problemVO.setSolutionNumber(problem.getSolutionNumber());
        problemVO.setCommentNumber(problem.getCommentNumber());
        problemVO.setRunTime(problem.getRunTime());
        problemVO.setRunMemory(problem.getRunMemory());
        if (isTag) {
            ArrayList<TagVO> tagVOs = new ArrayList<>();
            LambdaQueryWrapper<ProblemProblemTagPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProblemProblemTagPO::getProblemId, problem.getId());
            List<ProblemProblemTagPO> problemProblemTags = problemTagMapper.selectList(queryWrapper);
            for (ProblemProblemTagPO problemTag : problemProblemTags) {
                tagVOs.add(tagToVO(tagMapper.selectById(problemTag.getTagId())));
            }
            problemVO.setTags(tagVOs);
        }
        return problemVO;
    }

    private TagVO tagToVO(PageTagPO tag) {
        TagVO tagVO = new TagVO();
        tagVO.setId(tag.getId().toString());
        tagVO.setTagName(tag.getTagName());
        return tagVO;
    }

    private CategoryVO categoryToVO(PageCategoryPO pageCategory) {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(pageCategory.getId().toString());
        categoryVO.setCategoryName(pageCategory.getCategoryName());
        categoryVO.setDescription(pageCategory.getDescription());
        return categoryVO;
    }

    /**
     * 更新问题的缓存
     *
     * @param problemVO 新的问题的所有参数
     */
    @Override
    public void updateProblemCache(ProblemVO problemVO) {
        String problemVORedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(problemVO.getId());
        redisSever.setRedisKV(problemVORedisKey, JSONObject.toJSONString(Result.success(problemVO)));
    }

    /*
    private void updateEverydayCache(String id) {

        String everydayRedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                "getEveryday" + ":" +
                DigestUtils.md5Hex(id);
        List<ProblemArchives> problemArchives = JSONArray.parseArray(redisSever.getRedisKV(everydayRedisKey), ProblemArchives.class);
        long now = System.currentTimeMillis();
        if (problemArchives == null) {
            problemArchives = new ArrayList<>();
            ProblemArchives today = new ProblemArchives();
            today.setCount(1);
            today.setYear(Integer.valueOf(new SimpleDateFormat("yyyy").format(now)));
            today.setMonth(Integer.valueOf(new SimpleDateFormat("MM").format(now)));
            today.setDay(Integer.valueOf(new SimpleDateFormat("dd").format(now)));
            problemArchives.add(today);
        } else {
            ProblemArchives last = problemArchives.get(problemArchives.size() - 1);
            if (last.getYear().equals(Integer.valueOf(new SimpleDateFormat("yyyy").format(now))) &&
                    last.getMonth().equals(Integer.valueOf(new SimpleDateFormat("MM").format(now))) &&
                    last.getDay().equals(Integer.valueOf(new SimpleDateFormat("dd").format(now)))) {
                last.setCount(last.getCount() + 1);
            } else {
                ProblemArchives today = new ProblemArchives();
                today.setYear(Integer.valueOf(new SimpleDateFormat("yyyy").format(now)));
                today.setMonth(Integer.valueOf(new SimpleDateFormat("MM").format(now)));
                today.setDay(Integer.valueOf(new SimpleDateFormat("dd").format(now)));
                today.setCount(1);
                problemArchives.add(today);
            }
        }
        redisSever.setRedisKV(everydayRedisKey, JSONObject.toJSONString(problemArchives));
    }

    /**
     * 获取每天过题数量
     *
     * @return 数量列表
     */
    /*
    @Override
    public List<ProblemArchives> getEveryday() {
        SysUser sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        return problemSubmitMapper.getEveryday(sysUser.getId());
    }

    */

    /**
     * 获取题目数量
     *
     * @return 题库题目数量
     */
    @Override
    public Long getProblemCount() {
        LambdaQueryWrapper<ProblemPO> queryWrapper = new LambdaQueryWrapper<>();
        return problemMapper.selectCount(queryWrapper);
    }

    /**
     * 删除问题的缓存
     *
     * @param problemVO 问题参数
     */
    private void deleteProblemCache(ProblemVO problemVO) {
        String problemVORedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(problemVO.getId());
        redisSever.delKey(problemVORedisKey);
    }


}
