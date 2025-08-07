package com.example.gh_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "GH-API",
                version = "1.0",
                description = "API documentation for a hotel management system"
        )
)
@Configuration
public class OpenApiConfig {

}
