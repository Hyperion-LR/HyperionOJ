package com.hyperionoj.admin;

import com.hyperionoj.common.feign.AdminClients;
import com.hyperionoj.common.feign.AdminPageClients;
import com.hyperionoj.common.feign.ContestClients;
import com.hyperionoj.common.interceptor.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
@EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class, clients = {AdminClients.class, AdminPageClients.class, ContestClients.class})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.hyperionoj"})
@MapperScan("com.hyperionoj.admin.dao.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
