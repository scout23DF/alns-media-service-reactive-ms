package de.futurecompany.web.routes;

import de.futurecompany.services.NewsArticleService;
import de.futurecompany.web.handlers.NewsArticleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class ApplicationRoutes {

    private static final String NEWS_ARTICLES_ENDPOINT = "/api/articles/";

    @Bean
    public RouterFunction<ServerResponse> articleRoutes(NewsArticleHandler newsArticleHandler) {

        return route().POST(NEWS_ARTICLES_ENDPOINT, accept(APPLICATION_JSON), newsArticleHandler::createNewsArticle, ops -> ops.beanClass(NewsArticleService.class).beanMethod("addArticle")).build()
                      .and(route().GET(NEWS_ARTICLES_ENDPOINT + "{id}", accept(APPLICATION_JSON), newsArticleHandler::fetchArticle, ops -> ops.beanClass(NewsArticleService.class).beanMethod("fetchArticle")).build()
                      .and(route().GET(NEWS_ARTICLES_ENDPOINT, accept(APPLICATION_JSON), newsArticleHandler::listArticles, ops -> ops.beanClass(NewsArticleService.class).beanMethod("listArticles")).build())
               );
    }

}
