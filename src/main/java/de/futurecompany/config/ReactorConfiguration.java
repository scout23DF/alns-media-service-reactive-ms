package de.futurecompany.config;

import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;

@Configuration
public class ReactorConfiguration {

    public ReactorConfiguration() {
        Hooks.onOperatorDebug();
    }
}
