package de.futurecompany;

import java.util.List;

public class NewsArticle {

    private final String articleId;

    private String title;

    private String text;

    private List<ArticleAuthor> authors;

    public NewsArticle(String articleId, String title, String text, List<ArticleAuthor> authors) {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ArticleAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<ArticleAuthor> authors) {
        this.authors = authors;
    }

}
