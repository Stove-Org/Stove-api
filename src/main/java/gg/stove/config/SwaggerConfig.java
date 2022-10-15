package gg.stove.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Stove Api")
                .description("Stove Api Specifications.")
                .version("v1.0.0"));
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
