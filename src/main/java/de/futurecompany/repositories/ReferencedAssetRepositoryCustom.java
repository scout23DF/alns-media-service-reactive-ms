package de.futurecompany.repositories;

import de.futurecompany.models.ReferencedAsset;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReferencedAssetRepositoryCustom {

    <S extends ReferencedAsset> Mono<S> save(S entity);

    <S extends ReferencedAsset> Mono<S> insert(S entity);

    <S extends ReferencedAsset> Mono<S> update(S entity);

    Flux<ReferencedAsset> findAll();

    Mono<ReferencedAsset> findById(Long id);

    Mono<ReferencedAsset> findByArticleIdAndAssetURL(String articleId, String assetURL);

    Flux<ReferencedAsset> findAllBy(Pageable pageable);

    Flux<ReferencedAsset> findAllBy(Pageable pageable, Criteria criteria);

    Flux<ReferencedAsset> findByArticleId(String articleId);

    Flux<ReferencedAsset> findByAssetURL(String assetURL);

}