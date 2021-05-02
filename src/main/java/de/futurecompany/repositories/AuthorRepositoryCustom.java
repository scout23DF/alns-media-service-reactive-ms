package de.futurecompany.repositories;

import de.futurecompany.models.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorRepositoryCustom {

    <S extends Author> Mono<S> insert(S entity);
    <S extends Author> Mono<S> save(S entity);
    <S extends Author> Mono<S> update(S entity);

    Flux<Author> findAll();
    Mono<Author> findById(String id);
    Flux<Author> findAllBy(Pageable pageable);
    Flux<Author> findAllBy(Pageable pageable, Criteria criteria);
    
}
