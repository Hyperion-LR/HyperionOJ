package com.hyperionoj.web.infrastructure.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * @author Hyperion
 * @date 2023/3/20
 */
@Data
@Configuration
public class YarnConfig {

    @Value("${yarn.path}")
    private String path;

}
