package de.futurecompany.web.routes;

import de.futurecompany.web.handlers.NewsArticleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ApplicationRoutes {

    @Bean
    public RouterFunction<ServerResponse> articleRoutes(NewsArticleHandler articleHandler) {
        return RouterFunctions
            .route()
            .GET("/articles/", articleHandler::listArticles)
            .GET("/articles/{id}", articleHandler::fetchArticle)
            .build();
    }

}
