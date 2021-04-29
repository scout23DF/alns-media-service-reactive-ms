package de.futurecompany.web.handlers;

import de.futurecompany.models.NewsArticle;
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

    private final NewsArticleService articleService;

    public NewsArticleHandler(NewsArticleService articleService) {
        this.articleService = articleService;
    }

    public Mono<ServerResponse> createNewsArticle(ServerRequest serverRequest) {

        Mono<ArticleDTO> cricketerWrapper = serverRequest.bodyToMono(ArticleDTO.class);

        return cricketerWrapper.flatMap(oneNewArticle -> ServerResponse.ok()
                                                                       .contentType(MediaType.APPLICATION_JSON)
                                                                       .body(articleService.addArticle(oneNewArticle),
                                                                             ArticleDTO.class)
        );

    }

    public Mono<ServerResponse> fetchArticle(ServerRequest serverRequest) {
        var articleId = serverRequest.pathVariable("id");

        Mono<ArticleDTO> article = articleService.fetchArticle(articleId);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(article, ArticleDTO.class));
    }


    public Mono<ServerResponse> listArticles(ServerRequest serverRequest) {
        Flux<ArticleDTO> articles = articleService.listArticles();

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(articles, ArticleDTO.class));
    }

    public Mono<ServerResponse> exceptionExample(ServerRequest serverRequest) {
        throw new RuntimeException("RuntimeException occurred");
    }
}
