package com.hyperionoj.page;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Hyperion
 * @date 2021/11/26
 */

@SpringBootApplication(scanBasePackages = {"com.hyperionoj"})
@MapperScan("com.hyperionoj.page.dao.mapper")
public class PageApplication {
    public static void main(String[] args) {
        SpringApplication.run(PageApplication.class, args);
    }
}
