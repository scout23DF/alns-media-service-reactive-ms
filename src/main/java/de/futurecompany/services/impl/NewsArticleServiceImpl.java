package de.futurecompany.services.impl;

import de.futurecompany.exceptions.EntityAlreadyExistsException;
import de.futurecompany.models.enums.AppOperationTypeEnum;
import de.futurecompany.models.enums.ServiceLayerStatusProcessingEnum;
import de.futurecompany.repositories.NewsArticleRepository;
import de.futurecompany.services.AssetService;
import de.futurecompany.services.NewsArticleService;
import de.futurecompany.services.ReferencedAssetService;
import de.futurecompany.services.dtos.ArticleAssetsRequestDTO;
import de.futurecompany.services.dtos.ArticleDTO;
import de.futurecompany.services.dtos.AssetDTO;
import de.futurecompany.services.dtos.ReferencedAssetDTO;
import de.futurecompany.services.dtos.commons.AcknowledgeResultDTO;
import de.futurecompany.services.mappers.NewsArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NewsArticleServiceImpl implements NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final NewsArticleMapper newsArticleMapper;
    private final AssetService assetService;
    private final ReferencedAssetService referencedAssetService;

    public NewsArticleServiceImpl(NewsArticleRepository newsArticleRepository,
                                  NewsArticleMapper newsArticleMapper,
                                  AssetService assetService,
                                  ReferencedAssetService referencedAssetService) {

        this.newsArticleRepository = newsArticleRepository;
        this.newsArticleMapper = newsArticleMapper;
        this.assetService = assetService;
        this.referencedAssetService = referencedAssetService;
    }

    @Override
    public Mono<ArticleDTO> addArticle(ArticleDTO newArticleDTO) {

        log.debug("Request to Add new NewsArticle : {}", newArticleDTO);

        return newsArticleRepository.save(newsArticleMapper.toEntity(newArticleDTO))
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Mono<ArticleDTO> updateArticle(ArticleDTO articleToUpdateDTO) {

        log.debug("Request to Update one NewsArticle : {}", articleToUpdateDTO);

        return newsArticleRepository.save(newsArticleMapper.toEntity(articleToUpdateDTO))
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Mono<ArticleDTO> fetchArticleById(String id) {

        return newsArticleRepository.findById(id)
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Flux<ArticleDTO> listAllArticles() {

        return newsArticleRepository.findAll()
                                    .map(newsArticleMapper::toDTO);

    }

    @Override
    public Flux<ArticleDTO> searchArticlesByAuthorId(String authorId) {
        return newsArticleRepository.findByAuthorIdImpl(authorId)
                                    .map(newsArticleMapper::toDTO);
    }

    @Override
    public Mono<AcknowledgeResultDTO<List<ReferencedAssetDTO>>> addReferencedAssets(String articleId,
                                                          ArticleAssetsRequestDTO articleAssetsRequestDTO) {

        final AcknowledgeResultDTO<List<ReferencedAssetDTO>> ackResult = new AcknowledgeResultDTO<>(AppOperationTypeEnum.ADD_REFERENCED_ASSETS_TO_ARTICLE);

        if (articleAssetsRequestDTO != null && !CollectionUtils.isEmpty(articleAssetsRequestDTO.getAssetsURLToReferenceList())) {

            ackResult.setPayloadResponse(new ArrayList<>(articleAssetsRequestDTO.getAssetsURLToReferenceList().size()));

            final Flux<AssetDTO> assetsDTOFlux = assetService.searchAll()
                                                             .filter(oneAsset -> articleAssetsRequestDTO.getAssetsURLToReferenceList().contains(oneAsset.getUrl()));

            return handleReferencedAssetsCreation(articleId, assetsDTOFlux, ackResult);

        }

        return Mono.empty();
    }

    private Mono<AcknowledgeResultDTO<List<ReferencedAssetDTO>>> handleReferencedAssetsCreation(String articleId,
                                                                                                Flux<AssetDTO> assetsDTOFlux,
                                                                                                AcknowledgeResultDTO<List<ReferencedAssetDTO>> ackResult) {


        return assetsDTOFlux.map(theAssetDTO -> {

            referencedAssetService.searchByArticleIdAndAssetURL(articleId, theAssetDTO.getUrl())
                    .flatMap(oneRefDTO -> {
                        String msgError = "Asset URL: [" + theAssetDTO.getUrl() + "] is already referenced by Article Id: [" + articleId + "].";
                        return Mono.error(new EntityAlreadyExistsException(msgError));

                    }).switchIfEmpty(Mono.defer(() ->

                        referencedAssetService.create(ReferencedAssetDTO.builder()
                                                                        .articleId(articleId)
                                                                        .assetURL(theAssetDTO.getUrl())
                                                                        .referenceStartedOn(Instant.now())
                                                                        .qtyViews(0)
                                                                        .build()
                        ).map(theCreatedRefAssetDTO -> {
                            ackResult.getPayloadResponse().add(theCreatedRefAssetDTO);
                            return theCreatedRefAssetDTO;
                        })

                    ))
                    .doOnError(theException -> {

                        handleExceptionsAndMessages(articleId,
                                                    theAssetDTO.getUrl(),
                                                    theException,
                                                    HttpStatus.NOT_FOUND,
                                                    ServiceLayerStatusProcessingEnum.FAILURE,
                                                    ackResult);
                    })
                    .onErrorReturn(ackResult)
                    .subscribe();

            return theAssetDTO;

        }).then()
          .thenReturn(ackResult);

    }

    private void handleExceptionsAndMessages(String articleId,
                                             String assetURL,
                                             Throwable exceptionOccured,
                                             HttpStatus httpStatusToSet,
                                             ServiceLayerStatusProcessingEnum serviceLayerStatusProcessing,
                                             AcknowledgeResultDTO<List<ReferencedAssetDTO>> ackResult) {

        String exceptionErrorMsg = exceptionOccured.getMessage();

        log.error(exceptionErrorMsg);

        ackResult.setHttpStatusResponse(httpStatusToSet);

        if (exceptionOccured instanceof EntityAlreadyExistsException) {
            ackResult.addErrorOccurrenceWithHttpStatus(exceptionErrorMsg, null, HttpStatus.BAD_REQUEST, "[" + articleId + "]:[" + assetURL + "]");
            ackResult.setHttpStatusResponse(HttpStatus.BAD_REQUEST);
        } else if (exceptionOccured instanceof DataIntegrityViolationException) {
            exceptionErrorMsg = "[DataIntegrityViolationException]: Article Id: [" + articleId + "] and/or Asset URL: [" + assetURL + "] NOT FOUND. Make sure they were already created.";
            ackResult.addErrorOccurrenceWithHttpStatus(exceptionErrorMsg, null, HttpStatus.NOT_FOUND, "[" + articleId + "]:[" + assetURL + "]");
        } else {
            ackResult.addErrorOccurrence(exceptionErrorMsg, exceptionOccured);
        }
        ackResult.setServiceLayerStatusProcessing(serviceLayerStatusProcessing);
        ackResult.setResponseMessage(exceptionErrorMsg);

    }

}
