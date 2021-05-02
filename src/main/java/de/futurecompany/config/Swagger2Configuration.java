package de.futurecompany.config;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.webflux.core.converters.WebFluxSupportConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
// @Profile({"!prod && swagger"})
public class Swagger2Configuration implements WebFluxConfigurer {

    private final AppAlnsMediaServiceProperties appAlnsMediaServiceProperties;

    @Autowired
    public Swagger2Configuration(AppAlnsMediaServiceProperties pAppProperties) {
        this.appAlnsMediaServiceProperties = pAppProperties;
    }

    @Bean
    public OpenAPI customOpenAPI() {

        ModelConverters.getInstance().addConverter(new WebFluxSupportConverter());

        return new OpenAPI().components(new Components().addSecuritySchemes("JWE",
                                                                            new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWE")))
                .info(new Info().title(this.appAlnsMediaServiceProperties.getSwaggerPageTitle()).version(this.appAlnsMediaServiceProperties.getSwaggerPageAPIVersion()));
    }


    @Bean
    public GroupedOpenApi groupOpenApi() {
        String paths[] = {"/api/**"};
        String packagesToscan[] = {"de.futurecompany"};
        return GroupedOpenApi.builder()
                             .group("MediaService-ReactiveMS API's")
                             .pathsToMatch(paths)
                             .packagesToScan(packagesToscan)
                             .build();
    }

}