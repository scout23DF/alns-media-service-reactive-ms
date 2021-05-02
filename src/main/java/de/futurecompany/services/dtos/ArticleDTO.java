package de.futurecompany.services.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(builderClassName = "ArticleDTOBuilder", toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE) //Hides the constructor to force useage of the Builder.
@JsonDeserialize(builder = ArticleDTO.ArticleDTOBuilder.class)
public class ArticleDTO {

    private final String articleId;
    private final String title;
    private final String fullText;
    private Boolean published;
    private LocalDateTime publishingDateTime;

    private final String authorId;
    private final String authorName;


    @JsonPOJOBuilder(withPrefix = "")
    public static class ArticleDTOBuilder {
    }

}
