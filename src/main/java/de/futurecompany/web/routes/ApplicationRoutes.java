package de.futurecompany.web.routes;

import de.futurecompany.web.handlers.NewsArticleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ApplicationRoutes {

    private String NEWS_ARTICLES_ENDPOINT = "/api/articles/";

    @Bean
    public RouterFunction<ServerResponse> articleRoutes(NewsArticleHandler newsArticleHandler) {

        /*
        return RouterFunctions
            .route()
            .POST(NEWS_ARTICLES_ENDPOINT, newsArticleHandler::createNewsArticle)
            .GET(NEWS_ARTICLES_ENDPOINT + "{id}", newsArticleHandler::fetchArticle)
            .GET(NEWS_ARTICLES_ENDPOINT, newsArticleHandler::listArticles)
            .build();
        */

        return RouterFunctions.
                route(POST(NEWS_ARTICLES_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)), newsArticleHandler::createNewsArticle)
                .andRoute(GET(NEWS_ARTICLES_ENDPOINT + "id").and(accept(MediaType.APPLICATION_JSON)), newsArticleHandler::fetchArticle)
                .andRoute(GET(NEWS_ARTICLES_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)), newsArticleHandler::listArticles)
                ;
    }

    @Bean
    public RouterFunction<ServerResponse> errorRoute(NewsArticleHandler newsArticleHandler) {

        return RouterFunctions
                .route()
                .GET("/runtimeexception", newsArticleHandler::exceptionExample)
                .build();

    }

}
