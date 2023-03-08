package com.hyperionoj.web.infrastructure.config;

import com.hyperionoj.web.domain.interceptor.AdminLoginInterceptor;
import com.hyperionoj.web.domain.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private UserLoginInterceptor userLoginInterceptor;

    @Resource
    private AdminLoginInterceptor adminLoginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:80");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/problem/submit")
                .addPathPatterns("/problem/comment")
                .addPathPatterns("/problem/comment/support")
                .addPathPatterns("/problem/comment/delete")
                .addPathPatterns("/user/update")
                .addPathPatterns("/user/update/password")
        ;
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
        ;
    }
}
