package de.futurecompany.services.impl;

import de.futurecompany.models.NewsArticle;
import de.futurecompany.repositories.NewsArticleRepository;
import de.futurecompany.services.NewsArticleService;
import de.futurecompany.services.dtos.ArticleDTO;
import de.futurecompany.services.mappers.NewsArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class NewsArticleServiceImpl implements NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final NewsArticleMapper newsArticleMapper;

    public NewsArticleServiceImpl(NewsArticleRepository newsArticleRepository,
                                  NewsArticleMapper newsArticleMapper) {
        this.newsArticleRepository = newsArticleRepository;
        this.newsArticleMapper = newsArticleMapper;
    }

    @Override
    public Mono<ArticleDTO> addArticle(ArticleDTO newArticleDTO) {

        log.debug("Request to save NewsArticle : {}", newArticleDTO);

        return newsArticleRepository.save(newsArticleMapper.toEntity(newArticleDTO))
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Mono<ArticleDTO> fetchArticle(String id) {

        return newsArticleRepository.findById(id)
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Flux<ArticleDTO> listArticles() {

        return newsArticleRepository.findAll()
                                    .map(newsArticleMapper::toDTO);

    }

    private ArticleDTO outbound(NewsArticle article) {

        return ArticleDTO.builder()
                         .articleId(article.getArticleId())
                         .title(article.getTitle())
                         .fullText(article.getFullText())
                         .authorName(article.getAuthor().getName())
                         .build();
    }

}
