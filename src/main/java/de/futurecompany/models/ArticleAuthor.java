package de.futurecompany.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Table("tb_author")
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE) //Hides the constructor to force useage of the Builder.
public class ArticleAuthor {

    @Id
    @Column("id")
    @Size(max = 10)
    @Setter(AccessLevel.NONE)
    private final String id;

    @Column("ds_name")
    @NotNull
    @Size(max = 40)
    private String name;

    @Transient
    private List<NewsArticle> newsArticlesList;


}
