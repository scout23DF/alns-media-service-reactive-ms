package de.futurecompany.services.impl;

import de.futurecompany.models.ArticleAuthor;
import de.futurecompany.models.NewsArticle;
import de.futurecompany.repositories.NewsArticleRepository;
import de.futurecompany.services.NewsArticleService;
import de.futurecompany.services.dtos.ArticleDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class NewsArticleServiceImpl implements NewsArticleService {

    private final NewsArticleRepository weltArticleRepository;

    public NewsArticleServiceImpl(NewsArticleRepository weltArticleRepository) {
        this.weltArticleRepository = weltArticleRepository;
    }

    @Override
    public Mono<ArticleDTO> fetchArticle(String id) {
        return weltArticleRepository.get(id).map(this::outbound);
    }

    @Override
    public Flux<ArticleDTO> listArticles() {
        return weltArticleRepository.list().map(this::outbound);
    }

    private ArticleDTO outbound(NewsArticle article) {
        return new ArticleDTO(article.getArticleId(),
            article.getTitle(), article.getText(),
            article.getAuthors().stream().map(ArticleAuthor::getAuthorName).collect(Collectors.toList())
        );
    }

}
