package de.futurecompany.web.handlers;

import de.futurecompany.services.dtos.ArticleDTO;
import de.futurecompany.services.NewsArticleService;
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

    public Mono<ServerResponse> fetchArticle(ServerRequest request) {
        var articleId = request.pathVariable("id");

        Mono<ArticleDTO> article = articleService.fetchArticle(articleId);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromPublisher(article, ArticleDTO.class));
    }


    public Mono<ServerResponse> listArticles(ServerRequest serverRequest) {
        Flux<ArticleDTO> articles = articleService.listArticles();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromPublisher(articles, ArticleDTO.class));
    }
}
