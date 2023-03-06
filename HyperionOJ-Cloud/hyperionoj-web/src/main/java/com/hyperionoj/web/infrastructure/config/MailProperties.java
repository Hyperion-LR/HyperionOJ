package com.hyperionoj.web.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Data
@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    private String mailSender;

}
