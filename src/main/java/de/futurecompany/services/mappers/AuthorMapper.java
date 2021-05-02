package de.futurecompany.services.mappers;

import de.futurecompany.models.Author;
import de.futurecompany.services.dtos.AuthorDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Author} and its DTO {@link AuthorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {

}
