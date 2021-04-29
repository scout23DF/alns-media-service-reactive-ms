package de.futurecompany.services;

import de.futurecompany.models.NewsArticle;
import de.futurecompany.services.dtos.ArticleDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NewsArticleService {

    Mono<ArticleDTO> fetchArticle(String id);

    Flux<ArticleDTO> listArticles();

    Mono<ArticleDTO> addArticle(ArticleDTO newArticleDTO);

}
