package de.futurecompany.services.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "AuthorDTOBuilder", toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE) //Hides the constructor to force useage of the Builder.
@JsonDeserialize(builder = AuthorDTO.AuthorDTOBuilder.class)
public class AuthorDTO {

    private final Long id;
    private final String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class AuthorDTOBuilder {
    }


}
