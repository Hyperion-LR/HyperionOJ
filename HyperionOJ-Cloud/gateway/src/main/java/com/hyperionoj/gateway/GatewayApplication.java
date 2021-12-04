package com.hyperionoj.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Hyperion
 * @date 2021/11/26
 */
@SpringBootApplication(scanBasePackages = {"com.hyperionoj"})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
