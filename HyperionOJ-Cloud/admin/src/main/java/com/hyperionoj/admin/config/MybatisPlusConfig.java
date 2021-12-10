package com.hyperionoj.admin.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@Configuration
@MapperScan("com.hyperionoj.admin.dao.mapper")
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlushInterceptor = new MybatisPlusInterceptor();
        mybatisPlushInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlushInterceptor;
    }
}