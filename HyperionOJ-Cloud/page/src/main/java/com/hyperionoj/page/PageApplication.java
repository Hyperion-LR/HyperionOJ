package com.hyperionoj.page;

import com.hyperionoj.common.feign.OSSClients;
import com.hyperionoj.common.interceptor.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Hyperion
 * @date 2021/11/26
 */

@EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class, clients = {OSSClients.class})
@SpringBootApplication(scanBasePackages = {"com.hyperionoj"})
@MapperScan("com.hyperionoj.page.*.dao.mapper")
@EnableScheduling
public class PageApplication {
    public static void main(String[] args) {
        SpringApplication.run(PageApplication.class, args);
    }
}
