package de.futurecompany.services.mappers;

import de.futurecompany.models.NewsArticle;
import de.futurecompany.services.dtos.ArticleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link NewsArticle} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface NewsArticleMapper extends EntityMapper<ArticleDTO, NewsArticle> {

    @Mapping(source = "author.name", target = "authorName")
    ArticleDTO toDTO(NewsArticle newsArticleEntity);

}
