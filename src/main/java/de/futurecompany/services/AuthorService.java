package de.futurecompany.services;

import de.futurecompany.models.enums.AppOperationTypeEnum;
import de.futurecompany.models.enums.SingleOperationResponseEnum;
import de.futurecompany.services.dtos.commons.AcknowledgeResultDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Mono;

public interface AuthorService {

    Mono<AcknowledgeResultDTO> handleArticlePublishingById(@Parameter(in = ParameterIn.PATH, name = "pAuthorId") String authorId,
                                                                                        @Parameter(in = ParameterIn.PATH, name = "pArticleId") String articleId,
                                                                                        @Parameter(hidden = true) AppOperationTypeEnum appOperationType);

}
