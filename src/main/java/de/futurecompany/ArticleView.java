package de.futurecompany;

import java.util.List;

public class ArticleView {

    private final String articleId;
    private final String title;
    private final String text;
    private final List<String> authors;

    public ArticleView(String articleId, String title, String text, List<String> authors) {
        this.articleId = articleId;
        this.title = title;
        this.text = text;
        this.authors = authors;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getAuthors() {
        return authors;
    }
}
