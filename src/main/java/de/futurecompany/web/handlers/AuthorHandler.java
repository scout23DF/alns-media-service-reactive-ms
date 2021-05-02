package de.futurecompany.web.handlers;

import de.futurecompany.models.enums.AppOperationTypeEnum;
import de.futurecompany.services.AuthorService;
import de.futurecompany.services.dtos.commons.AcknowledgeResultDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthorHandler {

    private final AuthorService authorService;

    public AuthorHandler(AuthorService authorService) {
        this.authorService = authorService;
    }

    public Mono<ServerResponse> publishArticleById(ServerRequest serverRequest) {
        String authorId  = serverRequest.pathVariable("pAuthorId");
        String articleId = serverRequest.pathVariable("pArticleId");

        Mono<AcknowledgeResultDTO> ackResult = this.authorService.handleArticlePublishingById(authorId,
                                                                                              articleId,
                                                                                              AppOperationTypeEnum.PUBLISH_ARTICLE);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(ackResult, AcknowledgeResultDTO.class));
    }

    public Mono<ServerResponse> unpublishArticleById(ServerRequest serverRequest) {
        String authorId  = serverRequest.pathVariable("pAuthorId");
        String articleId = serverRequest.pathVariable("pArticleId");

        Mono<AcknowledgeResultDTO> ackResult = this.authorService.handleArticlePublishingById(authorId,
                                                                                              articleId,
                                                                                              AppOperationTypeEnum.UNPUBLISH_ARTICLE);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(ackResult, AcknowledgeResultDTO.class));
    }

}
