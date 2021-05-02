package de.futurecompany.web.handlers;

import de.futurecompany.services.AssetService;
import de.futurecompany.services.dtos.AssetDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AssetHandler {

    private final AssetService assetService;

    public AssetHandler(AssetService assetService) {
        this.assetService = assetService;
    }

    public Mono<ServerResponse> addNewAsset(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(AssetDTO.class)
                            .flatMap(oneAsset -> ServerResponse.ok()
                                                               .contentType(MediaType.APPLICATION_JSON)
                                                               .body(assetService.create(oneAsset),
                                                                     AssetDTO.class)
                            );

    }

    public Mono<ServerResponse> updateAsset(ServerRequest serverRequest) {
        String assetURL = serverRequest.pathVariable("pAssetURL");

        return serverRequest.bodyToMono(AssetDTO.class)
                            .flatMap(oneAsset -> ServerResponse.ok()
                                                               .contentType(MediaType.APPLICATION_JSON)
                                                               .body(assetService.update(assetURL, oneAsset),
                                                                     AssetDTO.class)
                            );

    }

    public Mono<ServerResponse> fetchAssetById(ServerRequest serverRequest) {
        String assetURL = serverRequest.pathVariable("pAssetURL");

        Mono<AssetDTO> assetDTOMono = assetService.searchById(assetURL);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(assetDTOMono, AssetDTO.class));
    }

    public Mono<ServerResponse> listAllAssets(ServerRequest serverRequest) {

        Flux<AssetDTO> assetDTOFlux = assetService.searchAll();

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(assetDTOFlux, AssetDTO.class));
    }

    public Mono<ServerResponse> listAllAssetsByAuthor(ServerRequest serverRequest) {
        String authorId = serverRequest.pathVariable("pAuthorId");

        Flux<AssetDTO> assetDTOFlux = assetService.searchAllByAuthorId(authorId);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(assetDTOFlux, AssetDTO.class));
    }

}
