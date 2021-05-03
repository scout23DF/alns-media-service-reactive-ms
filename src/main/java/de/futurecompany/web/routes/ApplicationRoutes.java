package de.futurecompany.web.routes;

import de.futurecompany.services.AssetService;
import de.futurecompany.services.AuthorService;
import de.futurecompany.services.NewsArticleService;
import de.futurecompany.services.ReferencedAssetService;
import de.futurecompany.web.handlers.AssetHandler;
import de.futurecompany.web.handlers.AuthorHandler;
import de.futurecompany.web.handlers.NewsArticleHandler;
import de.futurecompany.web.handlers.ReferencedAssetHandler;
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
    private static final String ASSETS_ENDPOINT = "/api/assets/";
    private static final String REFERENCED_ASSETS_ENDPOINT = "/api/referenced-assets/";

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
                            ops -> ops.beanClass(NewsArticleService.class).beanMethod("listAllArticles")).build()

          .and(route().POST(NEWS_ARTICLES_ENDPOINT + "{articleId}/referenced-assets/",
                            accept(APPLICATION_JSON),
                            newsArticleHandler::addReferencedAssets,
                            ops -> ops.beanClass(NewsArticleService.class).beanMethod("addReferencedAssets")).build()

        )));
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

    @Bean
    public RouterFunction<ServerResponse> assetRoutes(AssetHandler assetHandler) {

        return route().POST(ASSETS_ENDPOINT,
                            accept(APPLICATION_JSON),
                            assetHandler::addNewAsset,
                            ops -> ops.beanClass(AssetService.class).beanMethod("create")).build()

          .and(route().PUT(ASSETS_ENDPOINT + "{pAssetURL}",
                            accept(APPLICATION_JSON),
                            assetHandler::updateAsset,
                            ops -> ops.beanClass(AssetService.class).beanMethod("update")).build()

          .and(route().GET(ASSETS_ENDPOINT,
                           accept(APPLICATION_JSON),
                           assetHandler::listAllAssets,
                           ops -> ops.beanClass(AssetService.class).beanMethod("searchAll")).build()

          .and(route().GET(ASSETS_ENDPOINT + "{pAssetURL}",
                            accept(APPLICATION_JSON),
                            assetHandler::fetchAssetById,
                            ops -> ops.beanClass(AssetService.class).beanMethod("searchById")).build()

          .and(route().GET(ASSETS_ENDPOINT + "author/{pAuthorId}",
                            accept(APPLICATION_JSON),
                            assetHandler::listAllAssetsByAuthor,
                            ops -> ops.beanClass(AssetService.class).beanMethod("searchAllByAuthorId")).build()
        ))));
    }

    @Bean
    public RouterFunction<ServerResponse> referencedAssetRoutes(ReferencedAssetHandler referencedAssetHandler) {

        return route().POST(REFERENCED_ASSETS_ENDPOINT,
                            accept(APPLICATION_JSON),
                            referencedAssetHandler::addNewReferencedAsset,
                            ops -> ops.beanClass(ReferencedAssetService.class).beanMethod("create")).build()

          .and(route().PUT(REFERENCED_ASSETS_ENDPOINT + "{pAssetURL}",
                            accept(APPLICATION_JSON),
                            referencedAssetHandler::updateReferencecAsset,
                            ops -> ops.beanClass(ReferencedAssetService.class).beanMethod("update")).build()

          .and(route().GET(REFERENCED_ASSETS_ENDPOINT,
                            accept(APPLICATION_JSON),
                            referencedAssetHandler::listAllReferencedAssets,
                            ops -> ops.beanClass(ReferencedAssetService.class).beanMethod("searchAll")).build()

         .and(route().GET(REFERENCED_ASSETS_ENDPOINT + "{pId}",
                            accept(APPLICATION_JSON),
                            referencedAssetHandler::fetchReferencedAssetById,
                            ops -> ops.beanClass(ReferencedAssetService.class).beanMethod("searchById")).build()

         .and(route().GET(REFERENCED_ASSETS_ENDPOINT + "article/{pArticleId}",
                            accept(APPLICATION_JSON),
                            referencedAssetHandler::listReferencedAssetsByArticleId,
                            ops -> ops.beanClass(ReferencedAssetService.class).beanMethod("searchAllByArticleId")).build()

         .and(route().GET(REFERENCED_ASSETS_ENDPOINT + "asset/{pAssetURL}",
                            accept(APPLICATION_JSON),
                            referencedAssetHandler::listReferencedAssetsByAssetURL,
                            ops -> ops.beanClass(ReferencedAssetService.class).beanMethod("searchAllByAssetURL")).build()

         .and(route().GET(REFERENCED_ASSETS_ENDPOINT + "article/{pArticleId}/asset/{pAssetURL}",
                            accept(APPLICATION_JSON),
                            referencedAssetHandler::fetchReferencedAssetByArticleIdAndAssetURL,
                            ops -> ops.beanClass(ReferencedAssetService.class).beanMethod("searchByArticleIdAndAssetURL")).build()



         ))))));
    }



}
