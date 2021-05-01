package de.futurecompany.services;

import de.futurecompany.services.dtos.ArticleDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NewsArticleService {

    Mono<ArticleDTO> fetchArticle(@Parameter(in = ParameterIn.PATH) String id);

    Flux<ArticleDTO> listArticles();

    Mono<ArticleDTO> addArticle(ArticleDTO newArticleDTO);

}
