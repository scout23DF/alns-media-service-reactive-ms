package de.futurecompany.web.controllers;

import de.futurecompany.exceptions.EntityAlreadyExistsException;
import de.futurecompany.services.NewsArticleService;
import de.futurecompany.services.dtos.ArticleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/mvc/articles")
@Slf4j
public class NewsArticleController {

    private static final String ENTITY_NAME = "newsArticle";

    private final NewsArticleService articleService;

    public NewsArticleController(NewsArticleService articleService) {

        this.articleService = articleService;
    }

    /**
     * {@code POST  /articles} : Create a new Article.
     *
     * @param articleDTO the articleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countryDTO, or with status {@code 400 (Bad Request)} if the country has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public Mono<ResponseEntity<ArticleDTO>> createNewsArticle(@Valid @RequestBody ArticleDTO articleDTO) throws URISyntaxException {

        log.debug("REST request to save NewsArticle : {}", articleDTO);

        if (articleDTO.getArticleId() != null) {
            throw new EntityAlreadyExistsException("An Article already exists with this Id: " + articleDTO.getArticleId());
        }

        return articleService.addArticle(articleDTO)
                             .map( oneArticleDTO -> {
                                try {
                                    return ResponseEntity.created(new URI("/api/mvc/articles" + oneArticleDTO.getArticleId()))
                                                         .body(oneArticleDTO);
                                } catch (URISyntaxException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                );
    }



}
