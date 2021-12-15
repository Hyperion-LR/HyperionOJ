package com.hyperionoj.common.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.vo.CommentVo;
import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.ProblemVo;
import com.hyperionoj.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.hyperionoj.common.constants.Constants.*;


/**
 * @author Hyperion
 * @date 2021/12/12
 */

@Aspect
@Component
@Slf4j
public class CacheAspect {

    /**
     * 缓存过期时间加上随机数防止缓存击穿
     */
    private static final Random RANDOM = new Random();
    @Resource
    private RedisSever redisSever;

    @Pointcut("@annotation(com.hyperionoj.common.cache.Cache)")
    public void pt() {
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            Signature signature = pjp.getSignature();
            //类名
            String className = pjp.getTarget().getClass().getSimpleName();
            //调用的方法名
            String methodName = signature.getName();

            Class[] parameterTypes = new Class[pjp.getArgs().length];
            Object[] args = pjp.getArgs();
            //参数
            String params = "";
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                } else {
                    parameterTypes[i] = null;
                }
            }
            String toRedisParams = params;
            if (StringUtils.isNotEmpty(params)) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                toRedisParams = DigestUtils.md5Hex(toRedisParams);
            }
            Method method = pjp.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            //缓存过期时间
            long time = annotation.time();
            //缓存名称
            String name = annotation.name();
            //先从redis获取
            String redisKey = name + ":" + className + ":" + methodName + ":" + toRedisParams;
            String redisValue = redisSever.getRedisKV(redisKey);
            if (StringUtils.isNotEmpty(redisValue)) {
                log.info("读取缓存: {},{}", className, methodName);

                // 判断当前请求是否会改变其他变量如浏览量之类的数据
                return checkMethodName(className, methodName, redisKey, redisValue);
            }
            Object proceed = pjp.proceed();
            redisSever.setRedisKV(redisKey, JSON.toJSONString(proceed));
            log.info("更新缓存: {},{}", className, methodName);
            return proceed;
        } catch (Throwable throwable) {
            log.warn(throwable.getMessage());
        }
        return Result.fail(ErrorCode.SYSTEM_ERROR);
    }

    private Result checkMethodName(String className, String methodName, String redisKey, String redisValue) {

        Result result = JSON.parseObject(redisValue, Result.class);

        // 对评论点赞
        if (StringUtils.compare(className, PROBLEM_CONTROLLER) == 0 &&
                StringUtils.compare(methodName, PROBLEM_SUPPORT_COMMENT) == 0) {

            result.setData((Integer) result.getData() + 1);
            redisSever.setRedisKV(redisKey, JSONObject.toJSONString(result));
        }

        // 查看评论列表(有些评论的点赞数已经修改了)
        if (StringUtils.compare(className, PROBLEM_CONTROLLER) == 0 &&
                StringUtils.compare(methodName, GET_COMMENTS) == 0) {

            List<CommentVo> newCommentVo = new ArrayList<>();
            CommentVo commentVoTemp = new CommentVo();
            for (CommentVo commentVo : JSONArray.parseArray(JSONObject.toJSONString(result.getData()), CommentVo.class)) {
                commentVoTemp.setId(commentVo.getId());
                String commentVoRedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                        PROBLEM_CONTROLLER + ":" +
                        PROBLEM_SUPPORT_COMMENT + ":" +
                        DigestUtils.md5Hex(JSONObject.toJSONString(commentVoTemp));

                String redisComment = redisSever.getRedisKV(commentVoRedisKey);
                if (redisComment != null) {
                    commentVo.setSupportNumber((Integer) JSONObject.parseObject(redisComment, Result.class).getData());
                }
                newCommentVo.add(commentVo);
            }
            result.setData(newCommentVo);
            redisSever.setRedisKV(redisKey, JSONObject.toJSONString(result));
        }

        // 获取题目列表更新题目 评论数,题解数,提交数等
        if (StringUtils.compare(className, PROBLEM_CONTROLLER) == 0 &&
                StringUtils.compare(methodName, GET_PROBLEM_LIST) == 0) {
            List<ProblemVo> newProblemVoList = new ArrayList<>();
            for (ProblemVo problemVo : JSONArray.parseArray(JSONObject.toJSONString(result.getData()), ProblemVo.class)) {
                String problemVoRedisKey = REDIS_KAY_PROBLEM_CACHE + ":" +
                        PROBLEM_CONTROLLER + ":" +
                        GET_PROBLEM_ID + ":" +
                        DigestUtils.md5Hex(problemVo.getId());
                String redisProblemValue = redisSever.getRedisKV(problemVoRedisKey);
                if (redisProblemValue != null) {
                    ProblemVo newProblemVo = JSONObject.parseObject(JSONObject.toJSONString(JSONObject.parseObject(redisProblemValue, Result.class).getData()), ProblemVo.class);
                    // 评论数量
                    problemVo.setCommentNumber(newProblemVo.getCommentNumber());

                    // 题解数量
                    problemVo.setSolutionNumber(newProblemVo.getSolutionNumber());

                    // 提交数量
                    problemVo.setSubmitNumber(newProblemVo.getSubmitNumber());

                    // 通过数量
                    problemVo.setAcNumber(newProblemVo.getAcNumber());

                }
                newProblemVoList.add(problemVo);
            }
            result.setData(newProblemVoList);
            redisSever.setRedisKV(redisKey, JSONObject.toJSONString(result));
        }

        return result;
    }
}
