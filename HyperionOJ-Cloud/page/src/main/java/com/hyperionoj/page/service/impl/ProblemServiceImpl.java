package com.hyperionoj.page.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.UpdateSubmitVo;
import com.hyperionoj.page.dao.mapper.problem.*;
import com.hyperionoj.page.dao.pojo.problem.Problem;
import com.hyperionoj.page.dao.pojo.problem.ProblemBody;
import com.hyperionoj.page.dao.pojo.problem.ProblemSubmit;
import com.hyperionoj.page.service.ProblemService;
import com.hyperionoj.page.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private ProblemBodyMapper problemBodyMapper;

    @Resource
    private ProblemCategoryMapper problemCategoryMapper;

    @Resource
    private ProblemCommentMapper problemCommentMapper;

    @Resource
    private ProblemTagMapper problemTagMapper;

    @Resource
    private ProblemSubmitMapper problemSubmitMapper;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 返回题目列表
     *
     * @param pageParams 分页参数
     * @return 题目
     */
    @Override
    public List<ProblemVo> getProblemList(PageParams pageParams) {
        Page<Problem> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Problem> problemPage = problemMapper.listArticle(page, pageParams.getLevel(), pageParams.getCategoryId());
        return copyProblemList(problemPage.getRecords(), false);
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
        return problemToVo(problemMapper.selectById(id), true);
    }

    /**
     * 提交题目
     *
     * @param submitVo 用户提交数据
     * @return 本次提交情况
     */
    @Override
    public Object submit(SubmitVo submitVo) {
        if (!check(submitVo)) {
            RunResult runResult = new RunResult();
            runResult.setAuthorId(submitVo.getAuthorId());
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
            problemSubmit.setAuthorId(Long.parseLong(submitVo.getAuthorId()));
            problemSubmit.setCodeBody(submitVo.getCodeBody());
            problemSubmit.setRunMemory(result.getRunMemory());
            problemSubmit.setCodeLang(submitVo.getCodeLang());
            problemSubmit.setStatus(result.getVerdict());
            problemSubmit.setRunTime(result.getRunTime());
            problemSubmitMapper.insert(problemSubmit);
            UpdateSubmitVo updateSubmitVo = new UpdateSubmitVo();
            updateSubmitVo.setProblemId(problemSubmit.getProblemId());
            updateSubmitVo.setAuthorId(problemSubmit.getAuthorId());
            updateSubmitVo.setStatus(problemSubmit.getStatus());
            kafkaTemplate.send(KAFKA_TOPIC_SUBMIT_PAGE, JSONObject.toJSONString(updateSubmitVo));
        }
        return result;
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

    /**
     * 转换problem和vo对象
     *
     * @param problemPage problem页
     * @param isBody      是否需要加上body
     * @return vo对象列表
     */
    private List<ProblemVo> copyProblemList(List<Problem> problemPage, Boolean isBody) {
        List<ProblemVo> problemVoList = new ArrayList<>();
        for (Problem problem : problemPage) {
            problemVoList.add(problemToVo(problem, isBody));
        }
        return problemVoList;
    }

    /**
     * 转换problem和vo对象
     *
     * @param problem problem对象
     * @param isBody  是否需要加上body
     * @return vo对象
     */
    private ProblemVo problemToVo(Problem problem, boolean isBody) {
        ProblemVo problemVo = new ProblemVo();
        problemVo.setId(problem.getId().toString());
        problemVo.setTitle(problem.getTitle());
        problemVo.setBodyId(problem.getBodyId().toString());
        problemVo.setProblemLevel(problem.getProblemLevel());
        problemVo.setCategoryId(problem.getCategoryId().toString());
        problemVo.setAcNumber(problem.getAcNumber());
        problemVo.setSubmitNumber(problem.getSubmitNumber());
        problemVo.setSolutionNumber(problem.getSolutionNumber());
        problemVo.setCommentNumber(problem.getCommentNumber());
        if (isBody) {
            problemVo.setProblemBodyVo(problemBodyToVo(problemBodyMapper.selectById(problem.getBodyId())));
        }
        return problemVo;
    }

    /**
     * 转换problem和vo对象
     *
     * @param problemBody problemBody对象
     * @return vo对象
     */
    private ProblemBodyVo problemBodyToVo(ProblemBody problemBody) {
        if (ObjectUtils.isEmpty(problemBody)) {
            return null;
        }
        ProblemBodyVo problemBodyVo = new ProblemBodyVo();
        problemBodyVo.setContent(problemBody.getProblemBodyHtml());
        return problemBodyVo;
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
            System.out.println("is ok");
        }
    }

}
