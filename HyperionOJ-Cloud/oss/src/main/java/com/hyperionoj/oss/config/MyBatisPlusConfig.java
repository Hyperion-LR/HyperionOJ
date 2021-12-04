package com.hyperionoj.oss.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hyperion
 */
@Configuration
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlushInterceptor = new MybatisPlusInterceptor();
        mybatisPlushInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlushInterceptor;
    }
}
