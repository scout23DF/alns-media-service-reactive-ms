package de.futurecompany.services.mappers;

import de.futurecompany.models.NewsArticle;
import de.futurecompany.services.dtos.ArticleDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link NewsArticle} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NewsArticleMapper extends EntityMapper<ArticleDTO, NewsArticle> {

    // @Named("name")
    // @BeanMapping(ignoreByDefault = true)
    // @Mapping(target = "id", source = "id")
    // @Mapping(target = "name", source = "name")
    // ArticleDTO toDtoName(NewsArticle oneNewsArticle);

}
