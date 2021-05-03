package de.futurecompany.services;

import de.futurecompany.services.dtos.ReferencedAssetDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReferencedAssetService {

    Mono<ReferencedAssetDTO> create(ReferencedAssetDTO referencedAssetToAddDTO);

    Mono<ReferencedAssetDTO> update(@Parameter(in = ParameterIn.PATH, name = "pId") Long pId,
                                    ReferencedAssetDTO referencedAssetToUpdateDTO);

    Flux<ReferencedAssetDTO> searchAll();

    Mono<ReferencedAssetDTO> searchById(@Parameter(in = ParameterIn.PATH, name = "pId") Long pId);

    Mono<ReferencedAssetDTO> searchByArticleIdAndAssetURL(@Parameter(in = ParameterIn.PATH, name = "pArticleId") String pArticleId,
                                                          @Parameter(in = ParameterIn.PATH, name = "pAssetURL") String pAssetURL);

    Flux<ReferencedAssetDTO> searchAllByArticleId(@Parameter(in = ParameterIn.PATH, name = "pArticleId") String pArticleId);

    Flux<ReferencedAssetDTO> searchAllByAssetURL(@Parameter(in = ParameterIn.PATH, name = "pAssetURL") String pAssetURL);

}
