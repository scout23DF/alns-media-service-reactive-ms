package de.futurecompany.repositories;

import de.futurecompany.models.NewsArticle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NewsArticleRepositoryCustom {

    <S extends NewsArticle> Mono<S> save(S entity);

    <S extends NewsArticle> Mono<S> insert(S entity);

    <S extends NewsArticle> Mono<S> update(S entity);

    Flux<NewsArticle> findAll();

    Mono<NewsArticle> findById(String id);

    Flux<NewsArticle> findAllBy(Pageable pageable);

    Flux<NewsArticle> findAllBy(Pageable pageable, Criteria criteria);

    Flux<NewsArticle> findByAuthorIdImpl(String authorId);

}