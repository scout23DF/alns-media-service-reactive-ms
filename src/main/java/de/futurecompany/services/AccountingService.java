package de.futurecompany.services;

import de.futurecompany.models.RoyaltiesAssetsAuthorSummary;
import reactor.core.publisher.Flux;

public interface AccountingService {

    Flux<RoyaltiesAssetsAuthorSummary> searchAll();

}
