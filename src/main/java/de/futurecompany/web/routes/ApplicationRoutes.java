package de.futurecompany.web.routes;

import de.futurecompany.services.AuthorService;
import de.futurecompany.services.NewsArticleService;
import de.futurecompany.web.handlers.AuthorHandler;
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
    private static final String AUTHORS_ENDPOINT = "/api/authors/";

    @Bean
    public RouterFunction<ServerResponse> articleRoutes(NewsArticleHandler newsArticleHandler) {

        return route().POST(NEWS_ARTICLES_ENDPOINT,
                            accept(APPLICATION_JSON),
                            newsArticleHandler::createNewsArticle,
                            ops -> ops.beanClass(NewsArticleService.class).beanMethod("addArticle")).build()
          .and(route().GET(NEWS_ARTICLES_ENDPOINT + "{id}",
                            accept(APPLICATION_JSON),
                            newsArticleHandler::fetchArticleById,
                            ops -> ops.beanClass(NewsArticleService.class).beanMethod("fetchArticleById")).build()
          .and(route().GET(NEWS_ARTICLES_ENDPOINT,
                            accept(APPLICATION_JSON),
                            newsArticleHandler::listAllArticles,
                            ops -> ops.beanClass(NewsArticleService.class).beanMethod("listAllArticles")).build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> authorRoutes(AuthorHandler authorHandler) {

        return route().PUT(AUTHORS_ENDPOINT + "{pAuthorId}/{pArticleId}/publish",
                           accept(APPLICATION_JSON),
                           authorHandler::publishArticleById,
                           ops -> ops.beanClass(AuthorService.class).beanMethod("handleArticlePublishingById")).build()
          .and(route().PUT(AUTHORS_ENDPOINT + "{pAuthorId}/{pArticleId}/unpublish",
                           accept(APPLICATION_JSON),
                           authorHandler::unpublishArticleById,
                           ops -> ops.beanClass(AuthorService.class).beanMethod("handleArticlePublishingById")).build()
        );
    }

}
