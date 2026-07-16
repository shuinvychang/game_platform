package com.shuinvy.game_platform.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Game Platform API Document")
                .version("1.0.0");

        var jwtSchemeName = "JWT Auth";

        var securityRequirement = new SecurityRequirement()
                .addList(jwtSchemeName);

        var components = new Components()
                .addSecuritySchemes(jwtSchemeName,
                        new SecurityScheme()
                                .name(jwtSchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));

        var server = new Server()
                .url("https://api.gameplatform.local")
                .description("Development Server");

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components)
                .servers(List.of(server));
    }
}
