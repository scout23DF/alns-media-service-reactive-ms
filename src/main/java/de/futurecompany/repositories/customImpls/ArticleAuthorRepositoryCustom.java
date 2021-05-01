package de.futurecompany.repositories.customImpls;

import de.futurecompany.models.ArticleAuthor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArticleAuthorRepositoryCustom {

    <S extends ArticleAuthor> Mono<S> insert(S entity);
    <S extends ArticleAuthor> Mono<S> save(S entity);
    <S extends ArticleAuthor> Mono<S> update(S entity);

    Flux<ArticleAuthor> findAll();
    Mono<ArticleAuthor> findById(String id);
    Flux<ArticleAuthor> findAllBy(Pageable pageable);
    Flux<ArticleAuthor> findAllBy(Pageable pageable, Criteria criteria);
    
}
