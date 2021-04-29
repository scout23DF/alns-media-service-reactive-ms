package de.futurecompany.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    /*
    private final String articleId;
    private final String title;
    private final String text;
    private final List<String> authors;
    */

    private String articleId;
    private String title;
    private String text;
    private List<ArticleAuthorDTO> authors;

}
