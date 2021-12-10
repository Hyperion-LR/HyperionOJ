package com.hyperionoj.page.interceptor;

import com.alibaba.druid.support.spring.mvc.StatHandlerInterceptor;
import com.alibaba.fastjson.JSON;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.utils.JWTUtils;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.hyperionoj.common.constants.Constants.TOKEN;
import static com.hyperionoj.common.constants.Constants.UNDEFINED;

/**
 * @author Hyperion
 * @date 2021/12/3
 */
@Component
@Slf4j
public class LoginInterceptor extends StatHandlerInterceptor {

    @Resource
    private RedisSever redisSever;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("Admin-Token");
        Object adminId = JWTUtils.checkToken(token);
        String admin = redisSever.getRedisKV(TOKEN + token);
        if (adminId != null && admin != null) {
            ThreadLocalUtils.set(admin);
            return true;
        }

        token = request.getHeader("SysUser-Token");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (StringUtils.isBlank(token) || UNDEFINED.equals(token)) {
            Result result = Result.fail(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        Object sysUserId = JWTUtils.checkToken(token);
        String sysUser = redisSever.getRedisKV(TOKEN + token);
        if (sysUser == null || sysUserId == null) {
            return false;
        }
        ThreadLocalUtils.set(sysUser);
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
