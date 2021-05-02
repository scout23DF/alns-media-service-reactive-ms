package de.futurecompany.repositories;

import de.futurecompany.models.Asset;
import de.futurecompany.models.NewsArticle;
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
public interface AssetRepository extends R2dbcRepository<Asset, String>, AssetRepositoryCustom {

    Flux<Asset> findAllBy(Pageable pageable);

    @Query("SELECT * FROM tb_asset entity WHERE entity.author_id IS NULL")
    Flux<Asset> findAllWhereAuthorIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<Asset> findAll();

    @Override
    Mono<Asset> findById(String id);

    @Override
    <S extends Asset> Mono<S> save(S entity);
}
