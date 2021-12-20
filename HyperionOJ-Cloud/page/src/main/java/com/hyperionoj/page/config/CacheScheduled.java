package com.hyperionoj.page.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.vo.ArticleVo;
import com.hyperionoj.common.vo.CommentVo;
import com.hyperionoj.common.vo.ProblemVo;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.article.dao.mapper.ArticleMapper;
import com.hyperionoj.page.article.dao.pojo.Article;
import com.hyperionoj.page.problem.dao.mapper.ProblemCommentMapper;
import com.hyperionoj.page.problem.dao.mapper.ProblemMapper;
import com.hyperionoj.page.problem.dao.pojo.Problem;
import com.hyperionoj.page.problem.dao.pojo.ProblemComment;
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

    @Resource
    private ArticleMapper articleMapper;

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

    /**
     * 新建一个线程回写文章
     */
    @Scheduled(fixedRate = 20 * 1000)
    @Async("taskExecutor")
    public void updateArticle() {
        String redisKeyPrefix = "article" + ":" +
                ARTICLE_CONTROLLER + ":" +
                "findArticleById" + ":*";
        Set<String> keys = redisSever.getKeys(redisKeyPrefix);
        for (String key : keys) {
            Result result = JSONObject.parseObject(redisSever.getRedisKV(key), Result.class);
            ArticleVo articleVo = JSONObject.parseObject(JSONObject.toJSONString(result.getData()), ArticleVo.class);
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, articleVo.getId());
            if (articleVo.getViewCounts() != null) {
                updateWrapper.set(Article::getViewCount, articleVo.getViewCounts());
            }
            if (articleVo.getCommentCounts() != null) {
                updateWrapper.set(Article::getCommentCount, articleVo.getCommentCounts());
            }
            if (articleVo.getSupport() != null) {
                updateWrapper.set(Article::getSupport, articleVo.getSupport());
            }
            if (articleVo.getWeight() != null) {
                updateWrapper.set(Article::getWeight, articleVo.getWeight());
            }
            articleMapper.update(null, updateWrapper);
        }
    }

}
