package de.futurecompany.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
// @Profile({"!prod && swagger"})
// @EnableSwagger2WebFlux
public class Swagger2Configuration implements WebFluxConfigurer {

    private final AppAlnsMediaServiceProperties appAlnsMediaServiceProperties;

    @Autowired
    public Swagger2Configuration(AppAlnsMediaServiceProperties pAppProperties) {
        this.appAlnsMediaServiceProperties = pAppProperties;
    }

    /*
    @Bean
    public OpenAPI customOpenAPI() {

        ModelConverters.getInstance().addConverter(new WebFluxSupportConverter());

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("JWE", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWE")))
                .info(new Info()
                        .title("Marketplace API")
                        .version("A Que Funcionar"));
    }


    @Bean
    public GroupedOpenApi groupOpenApi() {
        String paths[] = {"/api/**"};
        String packagesToscan[] = {"de.futurecompany"};
        return GroupedOpenApi.builder().group("groups").pathsToMatch(paths).packagesToScan(packagesToscan)
                .build();
    }
    */

    /*
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.futurecompany"))
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

        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);

        registry.addResourceHandler("/swagger-ui.html**")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");


    }
    */
}