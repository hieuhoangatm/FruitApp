package com.dinhhieu.FruitWebApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration

public class OpenApiConfig {

    // http://localhost:8080/swagger-ui/
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info().title("API-Service document")
                                            .version("v1.0.0")
                                            .description("description")
                                            .license(new License().name("API Licence").url("http://domain.vn/license"))
                )
                .servers(List.of(new Server().url("http://localhost:8080").description("Server Test")))
//                .components(
//                        new Components()
//                                .addSecuritySchemes(
//                                        "bearerAuth",
//                                        new SecurityScheme()
//                                                .type(SecurityScheme.Type.HTTP)
//                                                .scheme("bearer")
//                                                .bearerFormat("JWT")
//                                )
//                )
//                .security(List.of(new SecurityRequirement().addList("bearerAuth")))
                ;
    }

    @Bean
    public GroupedOpenApi groupedOpenApi(){
        return GroupedOpenApi.builder()
                .group("api-service")
                .packagesToScan("com.dinhhieu.controller")
                .build();
    }
}
