package com.hyperionoj.common.interceptor;

import com.alibaba.cloud.commons.lang.StringUtils;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign配置文件
 *
 * @author Hyperion
 * @date 2021/12/10
 */
public class DefaultFeignConfiguration implements RequestInterceptor {
    @Bean
    public Logger.Level logLevel() {
        return Logger.Level.BASIC;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 从header获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("SysUser-Token");
        if (StringUtils.isNotBlank(token)) {
            //将token传递出去
            requestTemplate.header("SysUser-Token", token);
        }
        token = request.getHeader("Admin-Token");
        if (StringUtils.isNotBlank(token)) {
            //将token传递出去
            requestTemplate.header("Admin-Token", token);
        }
    }
}
