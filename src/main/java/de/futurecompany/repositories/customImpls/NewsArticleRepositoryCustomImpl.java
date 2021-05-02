package de.futurecompany.repositories.customImpls;

import de.futurecompany.models.NewsArticle;
import de.futurecompany.repositories.helpers.EntityManager;
import de.futurecompany.repositories.rowmappers.ArticleAuthorRowMapper;
import de.futurecompany.repositories.rowmappers.NewsArticleRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.relational.core.query.Criteria.where;

/**
 * Spring Data SQL reactive custom repository implementation for the NewsArticle entity.
 */

public class NewsArticleRepositoryCustomImpl implements NewsArticleRepositoryCustom {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ArticleAuthorRowMapper articleAuthorRowMapper;
    private final NewsArticleRowMapper newsArticleRowMapper;

    private static final Table entityTable = Table.aliased("tb_news_article", EntityManager.ENTITY_ALIAS);
    private static final Table articleAuthorTable = Table.aliased("tb_author", "article_author");

    public NewsArticleRepositoryCustomImpl(R2dbcEntityTemplate template,
                                           EntityManager entityManager,
                                           ArticleAuthorRowMapper authorMapper,
                                           NewsArticleRowMapper newsArticleMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.articleAuthorRowMapper = authorMapper;
        this.newsArticleRowMapper = newsArticleMapper;
    }

    @Override
    public Flux<NewsArticle> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<NewsArticle> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<NewsArticle> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = NewsArticleSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(ArticleAuthorSqlHelper.getColumns(articleAuthorTable, "article_author"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(articleAuthorTable)
            .on(Column.create("author_id", entityTable))
            .equals(Column.create("id", articleAuthorTable));

        String select = entityManager.createSelect(selectFrom, NewsArticle.class, pageable, criteria);
        String alias = entityTable.getReferenceName().getReference();
        String selectWhere = Optional
            .ofNullable(criteria)
            .map(
                crit ->
                    new StringBuilder(select)
                        .append(" ")
                        .append("WHERE")
                        .append(" ")
                        .append(alias)
                        .append(".")
                        .append(crit.toString())
                        .toString()
            )
            .orElse(select); // TODO remove once https://github.com/spring-projects/spring-data-jdbc/issues/907 will be fixed
        return db.sql(selectWhere).map(this::process);
    }

    @Override
    public Flux<NewsArticle> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<NewsArticle> findById(String id) {
        return createQuery(null, where("id").is(id)).one();
    }

    @Override
    public Flux<NewsArticle> findByAuthorIdImpl(String authorId) {
        return createQuery(null, where("author_id").is(authorId)).all();
    }

    private NewsArticle process(Row row, RowMetadata metadata) {
        NewsArticle entity = newsArticleRowMapper.apply(row, "e");
        entity.setAuthor(articleAuthorRowMapper.apply(row, "article_author"));
        return entity;
    }


    @Override
    public <S extends NewsArticle> Mono<S> save(S entity) {

        return this.findById(entity.getArticleId())
                   .switchIfEmpty(insert(entity))
                   .then(update(entity)).thenReturn(entity);
    }

    @Override
    public <S extends NewsArticle> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends NewsArticle> Mono<S> update(S entity) {
        return r2dbcEntityTemplate.update(entity);
    }

}

class NewsArticleSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("ds_title", table, columnPrefix + "_ds_title"));
        columns.add(Column.aliased("tx_article", table, columnPrefix + "_tx_article"));
        columns.add(Column.aliased("is_published", table, columnPrefix + "_is_published"));
        columns.add(Column.aliased("dt_publishing", table, columnPrefix + "_dt_publishing"));

        columns.add(Column.aliased("author_id", table, columnPrefix + "_author_id"));
        return columns;
    }
}
