package com.hyperionoj.oss;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Hyperion
 * @date 2021/11/26
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.hyperionoj"})
@MapperScan("com.hyperionoj.oss.dao.mapper")
@EnableDiscoveryClient
public class OSSApplication {
    public static void main(String[] args) {
        SpringApplication.run(OSSApplication.class, args);
        log.info("oss is running...");
    }
}
