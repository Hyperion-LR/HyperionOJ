package com.hyperionoj.web.infrastructure.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.hyperionoj.web.infrastructure.constants.Constants.MAIL_FROM;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Data
@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    @Value(MAIL_FROM)
    private String mailSender;

}
