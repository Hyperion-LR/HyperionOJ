package com.hyperionoj.page.problem.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.common.feign.OSSClients;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.page.*;
import com.hyperionoj.common.vo.params.PageParams;
import com.hyperionoj.page.common.dao.mapper.CategoryMapper;
import com.hyperionoj.page.common.dao.mapper.TagMapper;
import com.hyperionoj.page.common.dao.pojo.PageCategory;
import com.hyperionoj.page.common.dao.pojo.PageTag;
import com.hyperionoj.page.problem.dao.dos.ProblemArchives;
import com.hyperionoj.page.problem.dao.mapper.*;
import com.hyperionoj.page.problem.dao.pojo.*;
import com.hyperionoj.page.problem.service.ProblemService;
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
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
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
     * ??????????????????
     *
     * @param pageParams ????????????
     * @return ??????
     */
    @Override
    public List<ProblemVo> getProblemList(PageParams pageParams) {
        Page<Problem> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Problem> problemPage = problemMapper.problemList(page, pageParams.getLevel(), Long.getLong(pageParams.getCategoryId()));
        return copyProblemList(problemPage.getRecords(), false, true);
    }

    /**
     * ????????????id????????????
     *
     * @param id ??????id
     * @return ??????
     */
    @Override
    public ProblemVo getProblemById(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            return null;
        }
        return problemToVo(problemMapper.selectById(id), true, true);
    }

    /**
     * ????????????
     *
     * @param submitVo ??????????????????
     * @return ??????????????????
     */
    @Override
    public Object submit(SubmitVo submitVo) {
        SysUser sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        Problem problem = problemMapper.selectById(submitVo.getProblemId());
        submitVo.setRunTime(problem.getRunTime());
        submitVo.setRunMemory(problem.getRunMemory());
        submitVo.setCaseNumber(problem.getCaseNumber());
        ProblemVo problemVo = problemToVo(problemMapper.selectById(submitVo.getProblemId()), false, false);
        submitVo.setCreateTime(dateFormat.format(System.currentTimeMillis()));
        submitVo.setCaseNumber(problemVo.getCaseNumber());
        submitVo.setRunTime(problemVo.getRunTime());
        submitVo.setRunMemory(problemVo.getRunMemory());
        if (!check(submitVo)) {
            RunResult runResult = new RunResult();
            runResult.setAuthorId(sysUser.getId().toString());
            runResult.setProblemId(Long.parseLong(submitVo.getProblemId()));
            runResult.setMsg("?????????????????????????????????????????????");
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
                this.updateEverydayCache(JSONObject.toJSONString(sysUser.getId()));
            }
            this.updateProblemCache(problemVo);
            result.setSubmitTime(submitVo.getCreateTime());
            result.setSubmitId(problemSubmit.getId());
        }
        return result;
    }

    /**
     * ??????kafka????????????
     *
     * @param record ????????????
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
     * ????????????
     *
     * @param problemVo ????????????
     * @return ???????????????
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
     * ????????????
     *
     * @param problemVo ????????????
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
        if (null != problemBodyVo.getProblemId()) {
            problemBody.setId(Long.valueOf(problemBodyVo.getProblemId()));
        } else {
            problemBody.setProblemId(0L);
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
        LambdaUpdateWrapper<ProblemBody> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ProblemBody::getProblemId, problemVo.getId());
        updateWrapper.set(ProblemBody::getProblemBody, problemVo.getProblemBodyVo().getProblemBody());
        updateWrapper.set(ProblemBody::getProblemBodyHtml, problemVo.getProblemBodyVo().getProblemBodyHtml());
        problemBodyMapper.update(null, updateWrapper);
        problem.setCategoryId(Long.valueOf(problemVo.getCategory().getId()));
        problem.setProblemLevel(problemVo.getProblemLevel());
        problem.setRunMemory(problemVo.getRunMemory());
        problem.setRunTime(problemVo.getRunTime());
        problem.setCaseNumber(problemVo.getCaseNumber());
        if (problemVo.getAcNumber() == null) {
            problem.setAcNumber(0);
        } else {
            problem.setAcNumber(problemVo.getAcNumber());
        }
        if (problemVo.getSubmitNumber() == null) {
            problem.setSubmitNumber(0);
        } else {
            problem.setSubmitNumber(problemVo.getSubmitNumber());
        }
        if (problemVo.getSolutionNumber() == null) {
            problem.setSolutionNumber(0);
        } else {
            problem.setSolutionNumber(problemVo.getSolutionNumber());
        }
        if (problemVo.getCommentNumber() == null) {
            problem.setCommentNumber(0);
        } else {
            problem.setCommentNumber(problemVo.getCommentNumber());
        }
        return problem;
    }

    /**
     * ????????????
     *
     * @param problemVo ????????????
     */
    @Override
    public void deleteProblem(ProblemVo problemVo) {
        LambdaQueryWrapper<ProblemBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProblemBody::getProblemId, problemVo.getId());
        queryWrapper.last("limit 1");
        problemBodyMapper.delete(queryWrapper);
        problemMapper.deleteById(problemVo.getId());
        this.deleteProblemCache(problemVo);
    }

    /**
     * ????????????????????????
     *
     * @return ??????????????????
     */
    @Override
    public List<CategoryVo> getCategory() {
        LambdaQueryWrapper<PageCategory> queryWrapper = new LambdaQueryWrapper<>();
        List<PageCategory> problemCategories = problemCategoryMapper.selectList(queryWrapper);
        return problemCategoryToVoList(problemCategories);
    }

    private List<CategoryVo> problemCategoryToVoList(List<PageCategory> problemCategories) {
        ArrayList<CategoryVo> problemCategoryVos = new ArrayList<>();
        for (PageCategory category : problemCategories) {
            problemCategoryVos.add(problemCategoryToVo(category));
        }
        return problemCategoryVos;
    }

    private CategoryVo problemCategoryToVo(PageCategory category) {
        CategoryVo problemCategoryVo = new CategoryVo();
        problemCategoryVo.setId(category.getId().toString());
        problemCategoryVo.setCategoryName(category.getCategoryName());
        problemCategoryVo.setDescription(category.getDescription());
        return problemCategoryVo;
    }

    /**
     * ?????????????????????
     *
     * @param commentVo ??????????????????
     * @return ??????????????????
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
     * ????????????
     *
     * @param commentVo ????????????
     */
    @Override
    public void deleteComment(CommentVo commentVo) {
        LambdaUpdateWrapper<ProblemComment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ProblemComment::getId, commentVo.getId());
        updateWrapper.set(ProblemComment::getIsDelete, 1);
        problemCommentMapper.update(null, updateWrapper);
        LambdaQueryWrapper<ProblemComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProblemComment::getId, commentVo.getId());
        queryWrapper.select(ProblemComment::getProblemId);
        ProblemComment problemComment = problemCommentMapper.selectOne(queryWrapper);
        if (problemComment == null) {
            return;
        }
        queryWrapper.last("limit 1");
        String problemVoRedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(problemComment.getProblemId().toString());
        String redisKV = redisSever.getRedisKV(problemVoRedisKey);
        if (!StringUtils.isBlank(redisKV)) {
            ProblemVo problemVo = JSONObject.parseObject((String) JSONObject.parseObject(redisKV, Result.class).getData(), ProblemVo.class);
            problemVo.setCommentNumber(problemVo.getCommentNumber() - 1);
            redisSever.setRedisKV(problemVoRedisKey, JSONObject.toJSONString(Result.success(problemVo)));
        }

    }

    /**
     * ??????????????????
     *
     * @param pageParams ????????????
     * @return ????????????
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
        if (commentVo.getToUser() != null) {
            problemComment.setToUid(Long.getLong(commentVo.getToUser().getId()));
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
     * ??????????????????
     *
     * @param pageParams ??????????????????
     * @return ??????????????????????????????????????????
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
     * ??????????????????
     *
     * @param id ??????id
     * @return ????????????
     */
    @Override
    public SubmitVo getSubmitById(Long id) {
        LambdaQueryWrapper<ProblemSubmit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProblemSubmit::getId, id);
        return submitToVo(problemSubmitMapper.selectOne(queryWrapper), true);
    }

    /**
     * ??????????????????????????????
     *
     * @param commentVo ????????????
     * @return ???????????????
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
     * ??????????????????
     *
     * @param problemCategoryVo ????????????
     * @return ????????????
     */
    @Override
    public CategoryVo addCategory(CategoryVo problemCategoryVo) {
        PageCategory category = new PageCategory();
        BeanUtils.copyProperties(problemCategoryVo, category);
        problemCategoryMapper.insert(category);
        problemCategoryVo.setId(category.getId().toString());
        return problemCategoryVo;
    }

    /**
     * ??????????????????
     *
     * @param problemCategoryVo ????????????
     */
    @Override
    public void deleteCategory(CategoryVo problemCategoryVo) {
        problemCategoryMapper.deleteById(problemCategoryVo.getId());
    }

    /**
     * ????????????????????????
     *
     * @param submitVo ??????????????????
     * @return ??????????????????
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
     * ?????????????????????
     *
     * @param problemVo ???????????????????????????
     */
    @Override
    public void updateProblemCache(ProblemVo problemVo) {
        String problemVoRedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(problemVo.getId());
        redisSever.setRedisKV(problemVoRedisKey, JSONObject.toJSONString(Result.success(problemVo)));
    }

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
     * ????????????????????????
     *
     * @return ????????????
     */
    @Override
    public List<ProblemArchives> getEveryday() {
        SysUser sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        return problemSubmitMapper.getEveryday(sysUser.getId());
    }

    /**
     * ??????????????????
     *
     * @return ??????????????????
     */
    @Override
    public Long getProblemCount() {
        LambdaQueryWrapper<Problem> queryWrapper = new LambdaQueryWrapper<>();
        return problemMapper.selectCount(queryWrapper);
    }

    /**
     * ?????????????????????
     *
     * @param problemVo ????????????
     */
    private void deleteProblemCache(ProblemVo problemVo) {
        String problemVoRedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(problemVo.getId());
        redisSever.delKey(problemVoRedisKey);
    }


}
