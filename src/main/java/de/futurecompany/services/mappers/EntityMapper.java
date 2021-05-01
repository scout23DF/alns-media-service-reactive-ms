package de.futurecompany.services.mappers;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */
// @Mapper(componentModel = "spring", uses = {})
public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntityList(List<D> dtoList);

    List<D> toDTOList(List<E> entityList);

}
