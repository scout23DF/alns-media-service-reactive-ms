package de.futurecompany.services.impl;

import de.futurecompany.exceptions.EntityNotFoundExistsException;
import de.futurecompany.models.enums.AppOperationTypeEnum;
import de.futurecompany.models.enums.ServiceLayerStatusProcessingEnum;
import de.futurecompany.models.enums.SingleOperationResponseEnum;
import de.futurecompany.repositories.AuthorRepository;
import de.futurecompany.services.AuthorService;
import de.futurecompany.services.NewsArticleService;
import de.futurecompany.services.dtos.commons.AcknowledgeResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository articleAuthorRepository;
    private final NewsArticleService newsArticleService;

    public AuthorServiceImpl(AuthorRepository articleAuthorRepository,
                             NewsArticleService newsArticleService) {

        this.articleAuthorRepository = articleAuthorRepository;
        this.newsArticleService = newsArticleService;
    }

    @Override
    public Mono<AcknowledgeResultDTO> handleArticlePublishingById(String authorId,
                                                                  String articleId,
                                                                  AppOperationTypeEnum appOperationType) {

        AcknowledgeResultDTO ackResult = new AcknowledgeResultDTO(appOperationType);
        Mono<AcknowledgeResultDTO> monoAckResult = Mono.empty();

        log.debug("Request to handle Article '{}' Un/Publishing By Author Id : {}", articleId, authorId);

        monoAckResult = this.newsArticleService.searchArticlesByAuthorId(authorId)
                                              .filter(oneArticleDTO -> oneArticleDTO.getArticleId().equalsIgnoreCase(articleId))
                                              .switchIfEmpty(Mono.error(new EntityNotFoundExistsException("Article '" + articleId + "' not found for this Author Id '" + authorId + "'.")))
                                              .log(log.getName())
                                              .last()
                                              .map(theFoundArticleDTO -> {

                                                  if (appOperationType.equals(AppOperationTypeEnum.PUBLISH_ARTICLE)) {
                                                      theFoundArticleDTO.setIsPublished(Boolean.TRUE);
                                                      theFoundArticleDTO.setPublishedOn(LocalDateTime.now());
                                                  } else {
                                                      theFoundArticleDTO.setIsPublished(Boolean.FALSE);
                                                      theFoundArticleDTO.setPublishedOn(null);
                                                  }
                                                  this.newsArticleService.updateArticle(theFoundArticleDTO).subscribe();

                                                  ackResult.setServiceLayerStatusProcessing(ServiceLayerStatusProcessingEnum.SUCCESS);
                                                  ackResult.setPayloadResponse(SingleOperationResponseEnum.SUCCESS);
                                                  return ackResult;

                                            })
                                            .onErrorResume(oneException -> {
                                                ackResult.setServiceLayerStatusProcessing(ServiceLayerStatusProcessingEnum.FAILURE);
                                                ackResult.setPayloadResponse(SingleOperationResponseEnum.FAILURE);
                                                // ackResult.addErrorOccurrence(oneException.getMessage(), new EntityNotFoundExistsException(oneException.getMessage()));
                                                ackResult.setHttpStatusResponse(HttpStatus.NOT_FOUND);
                                                ackResult.setResponseMessage(oneException.getMessage());
                                                return Mono.just(ackResult);
                                            });

        return monoAckResult;
    }
}
