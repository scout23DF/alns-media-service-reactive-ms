package de.futurecompany;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NewsArticleService {

    private final NewsArticleRepository weltArticleRepository;

    public NewsArticleService(NewsArticleRepository weltArticleRepository) {
        this.weltArticleRepository = weltArticleRepository;
    }

    public Mono<ArticleView> fetchArticle(String id) {
        return weltArticleRepository.get(id).map(this::outbound);
    }

    public Flux<ArticleView> listArticles() {
        return weltArticleRepository.list().map(this::outbound);
    }

    private ArticleView outbound(NewsArticle article) {
        return new ArticleView(article.getArticleId(),
            article.getTitle(), article.getText(),
            article.getAuthors().stream().map(ArticleAuthor::getAuthorName).collect(Collectors.toList())
        );
    }

}
