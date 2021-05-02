package de.futurecompany.services;

import de.futurecompany.services.dtos.AssetDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssetService {

    Mono<AssetDTO> create(AssetDTO newAssetDTO);
    Mono<AssetDTO> update(@Parameter(in = ParameterIn.PATH, name = "pAssetURL") String assetURL, AssetDTO newAssetDTO);
    Flux<AssetDTO> searchAll();
    Mono<AssetDTO> searchById(@Parameter(in = ParameterIn.PATH, name = "pAssetURL") String assetURL);
    Flux<AssetDTO> searchAllByAuthorId(@Parameter(in = ParameterIn.PATH, name = "pAuthorId") String authorId);

}
