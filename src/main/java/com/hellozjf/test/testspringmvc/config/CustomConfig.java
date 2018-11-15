package com.hellozjf.test.testspringmvc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Jingfeng Zhou
 */
@Data
@Component
@ConfigurationProperties("custom")
public class CustomConfig {
    private Integer maxPartSize;
}
