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

    private final NewsArticleRepository weltArticleRepository;
    private final NewsArticleMapper newsArticleMapper;

    public NewsArticleServiceImpl(NewsArticleRepository weltArticleRepository,
                                  NewsArticleMapper newsArticleMapper) {
        this.weltArticleRepository = weltArticleRepository;
        this.newsArticleMapper = newsArticleMapper;
    }

    @Override
    public Mono<ArticleDTO> addArticle(ArticleDTO newArticleDTO) {

        log.debug("Request to save NewsArticle : {}", newArticleDTO);

        return weltArticleRepository.save(newsArticleMapper.toEntity(newArticleDTO))
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Mono<ArticleDTO> fetchArticle(String id) {

        return weltArticleRepository.get(id)
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Flux<ArticleDTO> listArticles() {

        return weltArticleRepository.list()
                                    .map(newsArticleMapper::toDTO);

    }

    private ArticleDTO outbound(NewsArticle article) {
        return new ArticleDTO(article.getArticleId(),
                              article.getTitle(),
                              article.getText(),
                              null
                              /*
                              article.getAuthors()
                                     .stream()
                                     .map(ArticleAuthor::getAuthorName)
                                     .collect(Collectors.toList()
                              )
                              */
        );
    }

}
