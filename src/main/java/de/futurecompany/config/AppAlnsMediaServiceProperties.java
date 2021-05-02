package de.futurecompany.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "alns-media-service-reactive.config")
public class AppAlnsMediaServiceProperties {

    private boolean enabled;
    private boolean shouldDropAllTablesOnStartup;

    private String swaggerPageTitle;
    private String swaggerPageDescription;
    private String swaggerPageTermsOfService;
    private String swaggerPageAPIVersion;

}
