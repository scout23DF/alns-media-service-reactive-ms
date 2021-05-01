package de.futurecompany.services.mappers;

import de.futurecompany.models.ArticleAuthor;
import de.futurecompany.services.dtos.AuthorDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ArticleAuthor} and its DTO {@link AuthorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ArticleAuthorMapper extends EntityMapper<AuthorDTO, ArticleAuthor> {

}
