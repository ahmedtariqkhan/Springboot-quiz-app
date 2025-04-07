package com.ahmed.QuizApp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

import java.util.Locale;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ahmed",
                        email = "ahmedkhansaduzai@gmail.com"
                ),
                description = "Open API Documentation For Spring Security",
                title = "OpenApi specification - Ahmed",
                version = "1.0",
                license = @License(
                        name = "License Name"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://google.com"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth Description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
