package de.futurecompany.repositories;

import de.futurecompany.models.RoyaltiesAssetsAuthorSummary;
import reactor.core.publisher.Flux;

public interface AccountingRepository {

    Flux<RoyaltiesAssetsAuthorSummary> findAll();

}