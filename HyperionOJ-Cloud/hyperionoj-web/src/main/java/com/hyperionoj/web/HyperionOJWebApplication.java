package com.hyperionoj.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.hyperionoj.web.infrastructure.mapper")
@SpringBootApplication(scanBasePackages = {"com.hyperionoj.web"})
@EnableTransactionManagement
public class HyperionOJWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyperionOJWebApplication.class, args);
    }
}
