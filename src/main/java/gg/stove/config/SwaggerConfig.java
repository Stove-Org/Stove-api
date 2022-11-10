package gg.stove.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi goodsGroupedOpenApi() {
        return GroupedOpenApi.builder()
            .group("stove-api")
            .pathsToMatch("/**")
            .addOpenApiCustomiser(buildSecurityOpenApi())
            .build();
    }

    @Profile("local")
    @Bean
    public OpenAPI localOpenApi() {
        return new OpenAPI()
            .info(new Info().title("Stove Api")
                .description("Stove Api Specifications.")
                .version("v1.0.0"));
    }

    @Profile("!local")
    @Bean
    public OpenAPI OpenApi() {
        return new OpenAPI()
            .info(new Info().title("Stove Api")
                .description("Stove Api Specifications.")
                .version("v1.0.0")).servers(
                    List.of(new Server().url("https://api.stove.gg"))
            );
    }

    public OpenApiCustomiser buildSecurityOpenApi() {
        return openApi -> openApi.addSecurityItem(new SecurityRequirement().addList("jwt token"))
            .getComponents()
            .addSecuritySchemes("jwt token", new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("bearer")
            );
    }
}
