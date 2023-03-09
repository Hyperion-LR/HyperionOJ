package com.hyperionoj.web.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.web.application.api.ProblemService;
import com.hyperionoj.web.domain.convert.MapStruct;
import com.hyperionoj.web.domain.repo.*;
import com.hyperionoj.web.presentation.dto.*;
import com.hyperionoj.web.presentation.dto.param.PageParams;
import com.hyperionoj.web.presentation.vo.Result;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
    private ProblemRepo problemRepo;

    @Resource
    private CategoryRepo categoryRepo;

    @Resource
    private ProblemCommentRepo problemCommentRepo;

    @Resource
    private TagRepo tagRepo;

    @Resource
    private ProblemTagRepo problemTagRepo;

    @Resource
    private ProblemSubmitRepo problemSubmitRepo;

    @Resource
    private UserRepo userRepo;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 返回题目列表
     *
     * @param pageParams 分页参数
     * @return 题目
     */
    @Override
    public List<ProblemVO> getProblemList(PageParams pageParams) {
        Page<ProblemPO> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<ProblemPO> problemPage = problemRepo.problemList(page, pageParams.getLevel(), Long.getLong(pageParams.getCategoryId()));
        return copyProblemList(problemPage.getRecords());
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
        return MapStruct.toVO(problemRepo.getById(id));
    }

    /**
     * 提交题目
     *
     * @param submitDTO 用户提交数据
     * @return 本次提交情况
     */
    @Override
    public Object submit(SubmitDTO submitDTO) {
        UserPO sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), UserPO.class);
        ProblemPO problem = problemRepo.getById(submitDTO.getProblemId());
        submitDTO.setRunTime(problem.getRunTime());
        submitDTO.setRunMemory(problem.getRunMemory());
        submitDTO.setCaseNumber(problem.getCaseNumber());
        ProblemVO problemVO = MapStruct.toVO(problemRepo.getById(submitDTO.getProblemId()));
        submitDTO.setCreateTime(dateFormat.format(System.currentTimeMillis()));
        submitDTO.setRunTime(problemVO.getRunTime());
        submitDTO.setRunMemory(problemVO.getRunMemory());
        if (!check(submitDTO)) {
            RunResult runResult = new RunResult();
            runResult.setAuthorId(sysUser.getId().toString());
            runResult.setProblemId(Long.parseLong(submitDTO.getProblemId()));
            runResult.setMsg("请不要使用系统命令或者非法字符");
            return runResult;
        }
        RunResult result = null;
        kafkaTemplate.send(KAFKA_TOPIC_SUBMIT, JSONObject.toJSONString(submitDTO));
        try {
            long start = System.currentTimeMillis();
            while (null == (result = SUBMIT_RESULT.get(submitDTO.getAuthorId() + UNDERLINE + submitDTO.getProblemId()))) {
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
            problemSubmit.setCodeBody(submitDTO.getCodeBody());
            problemSubmit.setRunMemory(result.getRunMemory());
            problemSubmit.setCodeLang(submitDTO.getCodeLang());
            problemSubmit.setStatus(result.getVerdict());
            problemSubmit.setRunTime(result.getRunTime());
            try {
                problemSubmit.setCreateTime(dateFormat.parse(submitDTO.getCreateTime()).getTime());
            } catch (ParseException e) {
                log.info(e.toString());
                problemSubmit.setCreateTime(System.currentTimeMillis());
            }
            problemSubmitRepo.save(problemSubmit);
            UpdateSubmitVO updateSubmitVO = UpdateSubmitVO.builder().build();
            updateSubmitVO.setProblemId(problemSubmit.getProblemId().toString());
            updateSubmitVO.setAuthorId(problemSubmit.getAuthorId().toString());
            updateSubmitVO.setStatus(problemSubmit.getStatus());
            kafkaTemplate.send(KAFKA_TOPIC_SUBMIT_PAGE, JSONObject.toJSONString(updateSubmitVO));
            problemVO.setSubmitNumber(problemVO.getSubmitNumber() + 1);
            if (result.getVerdict().equals(AC)) {
                problemVO.setAcNumber(problemVO.getAcNumber() + 1);
                // this.updateEverydayCache(JSONObject.toJSONString(sysUser.getId()));
            }
            this.updateProblemCache(problemVO);
            result.setSubmitTime(submitDTO.getCreateTime());
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
     * @param problemDTO 题目对象
     * @return 新加的题目
     */
    @Override
    public ProblemVO addProblem(ProblemDTO problemDTO) {
        problemDTO.setId(null);
        ProblemPO problem = MapStruct.toPO(problemDTO);
        problemRepo.save(problem);
        problemDTO.setId(String.valueOf(problem.getId()));
        problemDTO.getTags().forEach(tagDTO -> {
            ProblemTagPO problemTagPO = ProblemTagPO.builder()
                    .problemId(problem.getId())
                    .tagId(Long.parseLong(tagDTO.getId()))
                    .build();
            problemTagRepo.save(problemTagPO);
        });
        return MapStruct.toVO(problemDTO);
    }

    /**
     * 修改题目
     *
     * @param problemDTO 题目信息
     */
    @Override
    public ProblemVO updateProblem(ProblemDTO problemDTO) {
        ProblemPO problemPO = MapStruct.toPO(problemDTO);
        problemRepo.updateById(problemPO);
        this.deleteProblemCache(problemDTO.getId());
        return MapStruct.toVO(problemPO);
    }

    /**
     * 删除题目
     *
     * @param problemId 题目ID
     */
    @Override
    public Boolean deleteProblem(String problemId) {
        LambdaQueryWrapper<ProblemPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProblemPO::getId, problemId);
        queryWrapper.last("limit 1");
        problemRepo.removeById(problemId);
        this.deleteProblemCache(problemId);
        return true;
    }

    /**
     * 获取题目分类列表
     *
     * @return 题目所有类别
     */
    @Override
    public List<CategoryVO> getCategoryList() {
        LambdaQueryWrapper<CategoryPO> queryWrapper = new LambdaQueryWrapper<>();
        List<CategoryPO> problemCategories = categoryRepo.list(queryWrapper);
        return problemCategoryToVOList(problemCategories);
    }

    /**
     * 获取题目标签列表
     *
     * @return 题目所有标签
     */
    @Override
    public List<TagVO> getTagList(){
        return tagRepo.list().stream().map(MapStruct::toVO).collect(Collectors.toList());
    }

    private List<CategoryVO> problemCategoryToVOList(List<CategoryPO> problemCategories) {
        ArrayList<CategoryVO> problemCategoryVOs = new ArrayList<>();
        for (CategoryPO category : problemCategories) {
            problemCategoryVOs.add(problemCategoryToVO(category));
        }
        return problemCategoryVOs;
    }

    private CategoryVO problemCategoryToVO(CategoryPO category) {
        return MapStruct.toVO(category);
    }

    /**
     * 对题目进行评论
     *
     * @param commentDTO 用户提交评论
     * @return 本次提交情况
     */
    @Override
    public CommentVO comment(CommentDTO commentDTO) {
        ProblemCommentPO comment = MapStruct.toPO(commentDTO);
        comment.setIsDelete(0);
        problemCommentRepo.save(comment);
        commentDTO.setId(comment.getId().toString());
        commentDTO.setCreateDate(dateFormat.format(comment.getCreateTime()));
        String problemVORedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(commentDTO.getProblemId());
        String redisKV = redisUtils.getRedisKV(problemVORedisKey);
        if (redisKV != null) {
            ProblemVO problemVO = JSONObject.parseObject(JSONObject.toJSONString(JSONObject.parseObject(redisKV, Result.class).getData()), ProblemVO.class);
            problemVO.setCommentNumber(problemVO.getCommentNumber() + 1);
            redisUtils.setRedisKV(problemVORedisKey, JSONObject.toJSONString(Result.success(problemVO)));
        }
        return MapStruct.toVO(commentDTO);
    }

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     */
    @Override
    public Boolean deleteComment(Long commentId) {
        LambdaUpdateWrapper<ProblemCommentPO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ProblemCommentPO::getId, commentId);
        updateWrapper.set(ProblemCommentPO::getIsDelete, 1);
        problemCommentRepo.update(updateWrapper);
        LambdaQueryWrapper<ProblemCommentPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProblemCommentPO::getId, commentId);
        queryWrapper.select(ProblemCommentPO::getProblemId);
        ProblemCommentPO problemComment = problemCommentRepo.getOne(queryWrapper);
        if (problemComment == null) {
            return false;
        }
        queryWrapper.last("limit 1");
        String problemVORedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(problemComment.getProblemId().toString());
        String redisKV = redisUtils.getRedisKV(problemVORedisKey);
        if (!StringUtils.isBlank(redisKV)) {
            ProblemVO problemVO = JSONObject.parseObject((String) JSONObject.parseObject(redisKV, Result.class).getData(), ProblemVO.class);
            problemVO.setCommentNumber(problemVO.getCommentNumber() - 1);
            redisUtils.setRedisKV(problemVORedisKey, JSONObject.toJSONString(Result.success(problemVO)));
        }
        return true;
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
        IPage<ProblemCommentPO> commentPage = problemCommentRepo.getCommentList(page, pageParams.getProblemId());
        return commentPage.getRecords().stream().map(this::commentToVO).collect(Collectors.toList());
    }

    private CommentVO commentToVO(ProblemCommentPO comment) {
        CommentVO commentVO = CommentVO.builder().build();
        commentVO.setProblemId(comment.getProblemId().toString());
        commentVO.setAuthor(MapStruct.toVO(userRepo.getById(comment.getAuthorId().toString())));
        commentVO.setContent(comment.getContent());
        commentVO.setLevel(comment.getLevel());
        commentVO.setId(comment.getId().toString());
        commentVO.setParentId(comment.getParentId().toString());
        if (comment.getToUid() != null || comment.getToUid() != 0) {
            commentVO.setToUser(MapStruct.toVO(userRepo.getById(comment.getToUid().toString())));
        }
        commentVO.setSupportNumber(comment.getSupportNumber());
        commentVO.setCreateDate(dateFormat.format(comment.getCreateTime()));
        return commentVO;
    }

    private ProblemCommentPO voToComment(CommentDTO commentDTO) {
        ProblemCommentPO problemComment = new ProblemCommentPO();
        problemComment.setProblemId(Long.parseLong(commentDTO.getProblemId()));
        problemComment.setContent(commentDTO.getContent());
        problemComment.setAuthorId(Long.parseLong(commentDTO.getAuthor().getId()));
        problemComment.setIsDelete(0);
        problemComment.setSupportNumber(0);
        problemComment.setCreateTime(System.currentTimeMillis());
        problemComment.setLevel(commentDTO.getLevel());
        problemComment.setParentId(Long.getLong(commentDTO.getParentId()));
        if (commentDTO.getToUser() != null) {
            problemComment.setToUid(Long.parseLong(commentDTO.getToUser().getId()));
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
        IPage<ProblemSubmitPO> submitList = problemSubmitRepo.getSubmitList(page,
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
        return MapStruct.toVO(problemSubmitRepo.getOne(queryWrapper));
    }

    /**
     * 题目下该评论的点赞数
     *
     * @param commentId 评论参数
     * @return 目前得赞数
     */
    @Override
    public Integer support(Long commentId) {
        return problemCommentRepo.support(Long.valueOf(commentId));
    }

    private List<SubmitVO> submitToVOList(List<ProblemSubmitPO> submits) {
        ArrayList<SubmitVO> submitVOs = new ArrayList<>();
        for (ProblemSubmitPO submit : submits) {
            submitVOs.add(MapStruct.toVO(submit));
        }
        return submitVOs;
    }

    /**
     * 添加题目分类
     *
     * @param categoryDTO 分类信息
     * @return 分类情况
     */
    @Override
    public CategoryVO addCategory(CategoryDTO categoryDTO) {
        CategoryPO category = new CategoryPO();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryRepo.save(category);
        categoryDTO.setId(category.getId().toString());
        return MapStruct.toVO(categoryDTO);
    }

    /**
     * 删除题目分类
     *
     * @param categoryDTO 分类参数
     */
    @Override
    public Boolean deleteCategory(CategoryDTO categoryDTO) {
        return categoryRepo.removeById(categoryDTO.getId());
    }

    /**
     * 添加题目标签
     *
     * @param tagDTO 标签信息
     * @return 分类情况
     */
    @Override
    public TagVO addTag(TagDTO tagDTO) {
        TagPO tagPO = TagPO.builder()
                .tagName(tagDTO.getTagName())
                .build();
        tagRepo.save(tagPO);
        return MapStruct.toVO(tagPO);
    }

    /**
     * 添加题目标签
     *
     * @param tagDTO 标签信息
     * @return 分类情况
     */
    @Override
    public Boolean deleteTag(TagDTO tagDTO) {
        return tagRepo.removeById(tagDTO.getId());
    }

    /**
     * 检查用户提交参数
     *
     * @param submitDTO 用户提交数据
     * @return 是否通过检验
     */
    private boolean check(SubmitDTO submitDTO) {
        UserPO sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), UserPO.class);
        if (sysUser.getId().equals(Long.getLong(submitDTO.getAuthorId()))) {
            return false;
        }
        return !StringUtils.contains(submitDTO.getCodeBody(), ILLEGAL_CHAR_SYSTEM);
    }

    private List<ProblemVO> copyProblemList(List<ProblemPO> problemPage) {
        List<ProblemVO> problemVOList = new ArrayList<>();
        for (ProblemPO problem : problemPage) {
            problemVOList.add(MapStruct.toVO(problem));
        }
        return problemVOList;
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
        redisUtils.setRedisKV(problemVORedisKey, JSONObject.toJSONString(Result.success(problemVO)));
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
        return problemRepo.count(queryWrapper);
    }

    /**
     * 获取题目测试点
     *
     * @param problemId 题目ID
     */
    @Override
    public void getProblemCase(Long problemId) {

    }

    /**
     * 上传题目测试点
     *
     * @param problemId 题目ID
     */
    @Override
    public Boolean pushProblemCase(Long problemId, MultipartFile multipartFile) {
        return null;
    }

    /**
     * 删除问题的缓存
     *
     * @param problemId 问题ID
     */
    private void deleteProblemCache(String problemId) {
        String problemVORedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":" +
                DigestUtils.md5Hex(problemId);
        redisUtils.delKey(problemVORedisKey);
    }


}
