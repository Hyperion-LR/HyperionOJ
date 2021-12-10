package com.hyperionoj.common.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * Feign配置文件
 * @author Hyperion
 * @date 2021/12/10
 */
public class DefaultFeignConfiguration {
    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }
}
