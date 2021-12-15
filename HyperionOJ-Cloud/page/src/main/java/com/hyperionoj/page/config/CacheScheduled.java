package com.hyperionoj.page.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.vo.CommentVo;
import com.hyperionoj.common.vo.ProblemVo;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.dao.mapper.problem.ProblemCommentMapper;
import com.hyperionoj.page.dao.mapper.problem.ProblemMapper;
import com.hyperionoj.page.dao.pojo.problem.Problem;
import com.hyperionoj.page.dao.pojo.problem.ProblemComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

import static com.hyperionoj.common.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@Component
@Slf4j
public class CacheScheduled {

    @Resource
    private RedisSever redisSever;

    @Resource
    private ProblemCommentMapper problemCommentMapper;

    @Resource
    private ProblemMapper problemMapper;

    /**
     * 新建一个线程回写数据库评论信息
     */
    @Scheduled(fixedRate = 10 * 1000)
    @Async("taskExecutor")
    public void updateProblemComment() {
        String redisKeyPrefix = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_COMMENTS + ":*";
        Set<String> keys = redisSever.getKeys(redisKeyPrefix);
        for (String key : keys) {
            Result result = JSONObject.parseObject(redisSever.getRedisKV(key), Result.class);
            for (CommentVo commentVo : JSONArray.parseArray(JSONObject.toJSONString(result.getData()), CommentVo.class)) {
                LambdaUpdateWrapper<ProblemComment> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(ProblemComment::getId, commentVo.getId());
                updateWrapper.set(ProblemComment::getSupportNumber, commentVo.getSupportNumber());
                problemCommentMapper.update(null, updateWrapper);
            }
        }
    }

    /**
     * 新建一个线程回写题库题目
     */
    @Scheduled(fixedRate = 20 * 1000)
    @Async("taskExecutor")
    public void updateProblem() {
        String redisKeyPrefix = REDIS_KAY_PROBLEM_CACHE + ":" +
                PROBLEM_CONTROLLER + ":" +
                GET_PROBLEM_ID + ":*";
        Set<String> keys = redisSever.getKeys(redisKeyPrefix);
        for (String key : keys) {
            Result result = JSONObject.parseObject(redisSever.getRedisKV(key), Result.class);
            ProblemVo problemVo = JSONObject.parseObject(JSONObject.toJSONString(result.getData()), ProblemVo.class);
            LambdaUpdateWrapper<Problem> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Problem::getId, problemVo.getId());
            updateWrapper.set(Problem::getCommentNumber, problemVo.getCommentNumber());
            updateWrapper.set(Problem::getSolutionNumber, problemVo.getSolutionNumber());
            updateWrapper.set(Problem::getSubmitNumber, problemVo.getSubmitNumber());
            updateWrapper.set(Problem::getAcNumber, problemVo.getAcNumber());
            problemMapper.update(null, updateWrapper);
        }
    }

}
