package com.dinhhieu.FruitWebApp.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dtp21vpqm",
                "api_key", "634341471717662",
                "api_secret", "wxaAQJvX5pJvEpOIbLCN-Icv49U"));
    }
}
