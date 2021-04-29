package de.futurecompany.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAuthorDTO {

    private String id;
    private String authorName;

    /*
    public ArticleAuthorDTO(String id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }

    public String getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    */
}
