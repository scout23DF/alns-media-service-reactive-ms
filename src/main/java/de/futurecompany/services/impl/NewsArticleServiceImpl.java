package de.futurecompany.services.impl;

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

        log.debug("Request to Add new NewsArticle : {}", newArticleDTO);

        return newsArticleRepository.save(newsArticleMapper.toEntity(newArticleDTO))
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Mono<ArticleDTO> updateArticle(ArticleDTO articleToUpdateDTO) {

        log.debug("Request to Update one NewsArticle : {}", articleToUpdateDTO);

        return newsArticleRepository.save(newsArticleMapper.toEntity(articleToUpdateDTO))
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Mono<ArticleDTO> fetchArticleById(String id) {

        return newsArticleRepository.findById(id)
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Flux<ArticleDTO> listAllArticles() {

        return newsArticleRepository.findAll()
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Flux<ArticleDTO> searchArticlesByAuthorId(String authorId) {
        return newsArticleRepository.findByAuthorIdImpl(authorId)
                                    .map(newsArticleMapper::toDTO);
    }

}
