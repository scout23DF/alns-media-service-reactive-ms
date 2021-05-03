package de.futurecompany.repositories;

import de.futurecompany.models.ReferencedAsset;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the NewsArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferencedAssetRepository extends R2dbcRepository<ReferencedAsset, Long>, ReferencedAssetRepositoryCustom {

    Flux<ReferencedAsset> findAllBy(Pageable pageable);

    // just to avoid having unambigous methods
    @Override
    Flux<ReferencedAsset> findAll();

    @Override
    Mono<ReferencedAsset> findById(Long id);

    @Override
    <S extends ReferencedAsset> Mono<S> save(S entity);
}
