package de.futurecompany.web.handlers;

import de.futurecompany.models.RoyaltiesAssetsAuthorSummary;
import de.futurecompany.services.AccountingService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountingHandler {

    private final AccountingService accountingService;

    public AccountingHandler(AccountingService accountingService) {

        this.accountingService = accountingService;
    }


    public Mono<ServerResponse> listAllRoyaltiesAssetsByAuthorSummary(ServerRequest serverRequest) {

        Flux<RoyaltiesAssetsAuthorSummary> royaltiesAssetsAuthorSummaryFlux = accountingService.searchAll();

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(royaltiesAssetsAuthorSummaryFlux,
                                                               RoyaltiesAssetsAuthorSummary.class));
    }

}
