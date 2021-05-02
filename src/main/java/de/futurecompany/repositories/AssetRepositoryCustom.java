package de.futurecompany.repositories;

import de.futurecompany.models.Asset;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssetRepositoryCustom {

    <S extends Asset> Mono<S> save(S entity);

    <S extends Asset> Mono<S> insert(S entity);

    <S extends Asset> Mono<S> update(S entity);

    Flux<Asset> findAll();

    Mono<Asset> findById(String id);

    Flux<Asset> findAllBy(Pageable pageable);

    Flux<Asset> findAllBy(Pageable pageable, Criteria criteria);

    Flux<Asset> findByAuthorIdImpl(String authorId);

}