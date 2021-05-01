package de.futurecompany.repositories;

import de.futurecompany.models.NewsArticle;
import de.futurecompany.repositories.customImpls.NewsArticleRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the NewsArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsArticleRepository extends R2dbcRepository<NewsArticle, String>, NewsArticleRepositoryCustom {
    Flux<NewsArticle> findAllBy(Pageable pageable);

    @Query("SELECT * FROM tb_news_article WHERE entity.author_id = :id")
    Flux<NewsArticle> findByAuthor(Long authorId);

    @Query("SELECT * FROM tb_news_article entity WHERE entity.author_id IS NULL")
    Flux<NewsArticle> findAllWhereAuthorIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<NewsArticle> findAll();

    @Override
    Mono<NewsArticle> findById(String id);

    @Override
    <S extends NewsArticle> Mono<S> save(S entity);
}
