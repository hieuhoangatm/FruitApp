package com.dinhhieu.FruitWebApp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix="document")
public class DocumentProperties {
    private String storageDir;

    private String fixedDelay;

    private String from;

    private String to;
}
