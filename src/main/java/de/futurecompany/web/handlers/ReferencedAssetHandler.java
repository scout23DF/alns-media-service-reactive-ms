package de.futurecompany.web.handlers;

import de.futurecompany.services.ReferencedAssetService;
import de.futurecompany.services.dtos.ReferencedAssetDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReferencedAssetHandler {

    private final ReferencedAssetService referencedAssetService;

    public ReferencedAssetHandler(ReferencedAssetService referencedAssetService) {

        this.referencedAssetService = referencedAssetService;
    }

    public Mono<ServerResponse> addNewReferencedAsset(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(ReferencedAssetDTO.class)
                            .flatMap(oneAsset -> ServerResponse.ok()
                                                               .contentType(MediaType.APPLICATION_JSON)
                                                               .body(referencedAssetService.create(oneAsset),
                                                                     ReferencedAssetDTO.class)
                            );

    }

    public Mono<ServerResponse> updateReferencecAsset(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("pId"));

        return serverRequest.bodyToMono(ReferencedAssetDTO.class)
                            .flatMap(oneAsset -> ServerResponse.ok()
                                                               .contentType(MediaType.APPLICATION_JSON)
                                                               .body(referencedAssetService.update(id, oneAsset),
                                                                     ReferencedAssetDTO.class)
                            );

    }

    public Mono<ServerResponse> fetchReferencedAssetById(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("pId"));

        Mono<ReferencedAssetDTO> assetDTOMono = referencedAssetService.searchById(id);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(assetDTOMono, ReferencedAssetDTO.class));
    }

    public Mono<ServerResponse> fetchReferencedAssetByArticleIdAndAssetURL(ServerRequest serverRequest) {
        String articleId = serverRequest.pathVariable("pArticleId");
        String assetURL = serverRequest.pathVariable("pAssetURL");

        Mono<ReferencedAssetDTO> assetDTOMono = referencedAssetService.searchByArticleIdAndAssetURL(articleId, assetURL);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(assetDTOMono, ReferencedAssetDTO.class));
    }

    public Mono<ServerResponse> listAllReferencedAssets(ServerRequest serverRequest) {

        Flux<ReferencedAssetDTO> assetDTOFlux = referencedAssetService.searchAll();

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(assetDTOFlux, ReferencedAssetDTO.class));
    }

    public Mono<ServerResponse> listReferencedAssetsByArticleId(ServerRequest serverRequest) {
        String articleId = serverRequest.pathVariable("pArticleId");

        Flux<ReferencedAssetDTO> assetDTOFlux = referencedAssetService.searchAllByArticleId(articleId);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(assetDTOFlux, ReferencedAssetDTO.class));
    }

    public Mono<ServerResponse> listReferencedAssetsByAssetURL(ServerRequest serverRequest) {
        String assetURL = serverRequest.pathVariable("pAssetURL");

        Flux<ReferencedAssetDTO> assetDTOFlux = referencedAssetService.searchAllByAssetURL(assetURL);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(assetDTOFlux, ReferencedAssetDTO.class));
    }


}
