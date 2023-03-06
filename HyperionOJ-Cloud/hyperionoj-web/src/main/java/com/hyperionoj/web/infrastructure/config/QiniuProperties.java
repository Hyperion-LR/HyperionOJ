package com.hyperionoj.web.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Hyperion
 * @date 2021/11/16
 */
@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuProperties {

    private String accessKey;

    private String accessSecretKey;

    private String bucketName;

    private String url;

    private String zone;
}
