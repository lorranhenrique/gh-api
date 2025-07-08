package com.example.gh_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "GH-API",
                version = "3.5.2",
                description = "API documentation for a hotel management"
        )
)
@Configuration
public class OpenApiConfig {

}