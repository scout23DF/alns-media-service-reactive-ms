package de.futurecompany.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
// @Profile({"!prod && swagger"})
// @EnableSwagger2WebFlux
public class Swagger2Configuration implements WebFluxConfigurer {

    private final AppAlnsMediaServiceProperties appAlnsMediaServiceProperties;

    @Autowired
    public Swagger2Configuration(AppAlnsMediaServiceProperties pAppProperties) {
        this.appAlnsMediaServiceProperties = pAppProperties;
    }


    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                // .apis(RequestHandlerSelectors.basePackage("de.futurecompany"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(this.appAlnsMediaServiceProperties.getSwaggerPageTitle())
                                   .description(this.appAlnsMediaServiceProperties.getSwaggerPageDescription())
                                   .termsOfServiceUrl(this.appAlnsMediaServiceProperties.getSwaggerPageTermsOfService())
                                   .version(this.appAlnsMediaServiceProperties.getSwaggerPageAPIVersion())
                                   .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/swagger-ui.html**")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}