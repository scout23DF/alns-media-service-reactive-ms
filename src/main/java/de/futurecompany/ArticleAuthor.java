package de.futurecompany;

public class ArticleAuthor {

    private final String id;

    private String authorName;

    public ArticleAuthor(String id, String authorName) {
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
}
