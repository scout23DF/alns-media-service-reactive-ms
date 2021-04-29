package de.futurecompany.repositories;

import de.futurecompany.models.ArticleAuthor;
import de.futurecompany.models.NewsArticle;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsArticleRepository {

    private static final List<NewsArticle> staticArticles = Collections.singletonList(
        new NewsArticle("1", "Very nice article", "Hello there. I'm not really interesting to read without images", Collections.singletonList(new ArticleAuthor("author1", "Stefan B.")))
    );

    public Mono<NewsArticle> get(String articleId) {
        Optional<NewsArticle> article = staticArticles.stream().filter((a) -> a.getArticleId().equals(articleId)).findFirst();

        return Mono.just(article).flatMap(Mono::justOrEmpty);
    }

    public Flux<NewsArticle> list() {
        return Flux.fromIterable(staticArticles);
    }

    public Mono<NewsArticle> save(NewsArticle toEntity) {

        Optional<NewsArticle> article = staticArticles.stream().filter((a) -> a.getArticleId().equals("1")).findFirst();

        return Mono.just(article).flatMap(Mono::justOrEmpty);
    }
}
