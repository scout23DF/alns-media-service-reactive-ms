package de.futurecompany.services;

import de.futurecompany.services.dtos.ArticleAssetsRequestDTO;
import de.futurecompany.services.dtos.ArticleDTO;
import de.futurecompany.services.dtos.ReferencedAssetDTO;
import de.futurecompany.services.dtos.commons.AcknowledgeResultDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface NewsArticleService {

    Mono<ArticleDTO> fetchArticleById(@Parameter(in = ParameterIn.PATH) String id);

    Flux<ArticleDTO> listAllArticles();

    Flux<ArticleDTO> searchArticlesByAuthorId(String authorId);

    Mono<ArticleDTO> addArticle(ArticleDTO newArticleDTO);

    Mono<ArticleDTO> updateArticle(ArticleDTO theFoundArticleDTO);

    Mono<AcknowledgeResultDTO<List<ReferencedAssetDTO>>> addReferencedAssets(@NotNull @Parameter(in = ParameterIn.PATH) String articleId,
                                                                             @NotNull @Valid ArticleAssetsRequestDTO articleAssetsRequestDTO);

}
