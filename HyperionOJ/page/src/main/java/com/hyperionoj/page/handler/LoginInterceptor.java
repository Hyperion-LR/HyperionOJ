package com.hyperionoj.page.handler;

import com.alibaba.druid.support.spring.mvc.StatHandlerInterceptor;
import com.alibaba.fastjson.JSON;
import com.hyperionoj.common.pojo.vo.ErrorCode;
import com.hyperionoj.common.pojo.vo.Result;
import com.hyperionoj.common.utils.JWTUtils;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.hyperionoj.common.constants.Constants.UNDEFINED;

/**
 * @author Hyperion
 * @date 2021/12/3
 */
@Component
@Slf4j
public class LoginInterceptor extends StatHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");

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
        Object sysUser = JWTUtils.checkToken(token);
        if (sysUser == null) {
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
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception error) throws Exception {
        ThreadLocalUtils.remove();
    }
}
