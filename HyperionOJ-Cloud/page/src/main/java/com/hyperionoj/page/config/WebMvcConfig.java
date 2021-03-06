package com.hyperionoj.page.config;

import com.hyperionoj.page.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
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
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/update/*")
                .addPathPatterns("/destroy")
                .addPathPatterns("/problem/submit")
                .addPathPatterns("/problem/comment")
                .addPathPatterns("/problem/everyday/**")
                .addPathPatterns("/article/publish")
                .addPathPatterns("/article/delete/**")
                .addPathPatterns("/article/comments/comment")
                .addPathPatterns("/article/admin/**")
                .addPathPatterns("/school/**")
                .addPathPatterns("/contest/**")
                .excludePathPatterns("/contest/endList")
                .excludePathPatterns("/contest/notStartList")
                .excludePathPatterns("/contest/proceedList")
                .excludePathPatterns("/contest/user/rank/**")
        ;
    }
}
