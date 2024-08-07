package com.dinhhieu.FruitWebApp.config;

import com.groupdocs.cloud.conversion.api.ConvertApi;
import com.groupdocs.cloud.conversion.client.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;



@org.springframework.context.annotation.Configuration
public class GroupDocsConfig {
    @Value("${groupdocs.app.sid}")
    private String appSid;

    @Value("${groupdocs.app.key}")
    private String appKey;

    @Bean
    public ConvertApi convertApi() {
        Configuration config = new Configuration(appSid, appKey);
        return new ConvertApi(config);
    }

}
