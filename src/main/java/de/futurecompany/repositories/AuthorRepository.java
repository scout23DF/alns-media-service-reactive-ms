package de.futurecompany.repositories;

import de.futurecompany.models.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, String>, AuthorRepositoryCustom {

    Flux<Author> findAllBy(Pageable pageable);

    // just to avoid having unambigous methods
    @Override
    Flux<Author> findAll();

    @Override
    Mono<Author> findById(String id);

    @Override
    <S extends Author> Mono<S> save(S entity);
}
