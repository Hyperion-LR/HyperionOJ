package com.hyperionoj.web.domain.interceptor;

import com.alibaba.druid.support.spring.mvc.StatHandlerInterceptor;
import com.alibaba.fastjson.JSON;
import com.hyperionoj.web.infrastructure.constants.Constants;
import com.hyperionoj.web.presentation.vo.ErrorCode;
import com.hyperionoj.web.presentation.vo.Result;
import com.hyperionoj.web.infrastructure.utils.JWTUtils;
import com.hyperionoj.web.infrastructure.utils.RedisUtils;
import com.hyperionoj.web.infrastructure.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Hyperion
 * @date 2021/12/4
 */
@Component
@Slf4j
public class LoginInterceptor extends StatHandlerInterceptor {

    @Resource
    private RedisUtils redisSever;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Admin-Token");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (StringUtils.isBlank(token) || Constants.UNDEFINED.equals(token)) {
            Result result = Result.fail(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        Object adminId = JWTUtils.checkToken(token);
        String admin = redisSever.getRedisKV(Constants.TOKEN + token);
        if (admin == null || adminId == null) {
            Result result = Result.fail(ErrorCode.TOKEN_ERROR);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        ThreadLocalUtils.set(admin);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception error) {
        ThreadLocalUtils.remove();
    }
}
