package de.futurecompany.repositories;

import de.futurecompany.models.ArticleAuthor;
import de.futurecompany.repositories.customImpls.ArticleAuthorRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ArticleAuthorRepository extends ReactiveCrudRepository<ArticleAuthor, String>, ArticleAuthorRepositoryCustom {

    Flux<ArticleAuthor> findAllBy(Pageable pageable);

    // just to avoid having unambigous methods
    @Override
    Flux<ArticleAuthor> findAll();

    @Override
    Mono<ArticleAuthor> findById(String id);

    @Override
    <S extends ArticleAuthor> Mono<S> save(S entity);
}
