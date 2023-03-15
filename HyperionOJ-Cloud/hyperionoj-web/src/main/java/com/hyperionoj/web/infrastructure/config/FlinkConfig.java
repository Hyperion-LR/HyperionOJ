package com.hyperionoj.web.infrastructure.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hyperion
 * @date 2023/3/15
 */
@Data
@Configuration
public class FlinkConfig {

    @Value("${flink.path}")
    private String path;

}
