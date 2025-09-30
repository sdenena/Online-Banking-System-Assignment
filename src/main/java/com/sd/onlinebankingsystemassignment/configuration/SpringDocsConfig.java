package com.sd.onlinebankingsystemassignment.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@OpenAPIDefinition
public class SpringDocsConfig {
    @Value("${application.title}")
    String title = "";

    @Value("${application.version}")
    String version = "1.0.0";

    @Bean
    public OpenAPI openAPIConfig() {
        String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement().addList(securitySchemeName)
                )
                .specVersion(SpecVersion.V31)
                .servers(
                        Collections.singletonList(
                                new Server().url("http://localhost:8080").description("Local endpoint")
                        )
                )
                .info(
                        new Info()
                                .title(title + " REST API")
                                .termsOfService("Term and condition")
                                .version(version)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
