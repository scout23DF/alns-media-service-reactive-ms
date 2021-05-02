package de.futurecompany.web.handlers;

import de.futurecompany.services.NewsArticleService;
import de.futurecompany.services.dtos.ArticleDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class NewsArticleHandler {

    private final NewsArticleService newsArticleService;

    public NewsArticleHandler(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    public Mono<ServerResponse> createNewsArticle(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(ArticleDTO.class)
                            .flatMap(oneNewArticle -> ServerResponse.ok()
                                                                    .contentType(MediaType.APPLICATION_JSON)
                                                                    .body(newsArticleService.addArticle(oneNewArticle),
                                                                          ArticleDTO.class)
        );

    }

    public Mono<ServerResponse> fetchArticleById(ServerRequest serverRequest) {
        String articleId = serverRequest.pathVariable("id");

        Mono<ArticleDTO> article = newsArticleService.fetchArticleById(articleId);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(article, ArticleDTO.class));
    }


    public Mono<ServerResponse> listAllArticles(ServerRequest serverRequest) {
        Flux<ArticleDTO> articles = newsArticleService.listAllArticles();

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(articles, ArticleDTO.class));
    }

}
