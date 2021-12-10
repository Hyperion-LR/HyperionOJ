package com.hyperionoj.admin;

import com.hyperionoj.common.config.DefaultFeignConfiguration;
import com.hyperionoj.common.feign.AdminClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
@EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class, clients = {AdminClients.class})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.hyperionoj"})
@MapperScan("com.hyperionoj.admin.dao.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
