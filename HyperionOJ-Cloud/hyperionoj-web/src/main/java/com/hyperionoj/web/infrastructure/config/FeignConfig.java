package com.hyperionoj.web.infrastructure.config;

import feign.Contract;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootConfiguration
public class FeignConfig {

    @Bean
    public Contract contract(){
        return new feign.Contract.Default();
    }
}
