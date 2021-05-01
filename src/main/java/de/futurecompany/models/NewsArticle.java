package de.futurecompany.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table("tb_news_article")
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE) //Hides the constructor to force useage of the Builder.
public class NewsArticle {

    @Id
    @Column("id")
    @Size(max = 60)
    @Setter(AccessLevel.NONE)
    private final String articleId;

    @Column("ds_title")
    @NotNull
    @Size(max = 255)
    private String title;

    @Column("tx_article")
    private String fullText;

    @Column("author_id")
    private String authorId;


    @Transient
    private ArticleAuthor author;

}
