package com.hyperionoj.web.infrastructure.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
@Data
@Configuration
public class JobResourceDirConfig {

    @Value("${jobDir.resource}")
    private String resourceDir;

}
