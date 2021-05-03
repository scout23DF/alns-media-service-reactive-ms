package de.futurecompany.repositories.customImpls;

import de.futurecompany.models.ReferencedAsset;
import de.futurecompany.repositories.ReferencedAssetRepositoryCustom;
import de.futurecompany.repositories.helpers.EntityManager;
import de.futurecompany.repositories.rowmappers.AssetRowMapper;
import de.futurecompany.repositories.rowmappers.NewsArticleRowMapper;
import de.futurecompany.repositories.rowmappers.ReferencedAssetRowMapper;
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
 * Spring Data SQL reactive custom repository implementation for the Asset entity.
 */

public class ReferencedAssetRepositoryCustomImpl implements ReferencedAssetRepositoryCustom {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ReferencedAssetRowMapper referencedAssetRowMapper;
    private final NewsArticleRowMapper newsArticleRowMapper;
    private final AssetRowMapper assetRowMapper;

    private static final Table entityTable = Table.aliased("tb_referenced_asset", EntityManager.ENTITY_ALIAS);
    private static final Table newsArticleTable = Table.aliased("tb_news_article", "article");
    private static final Table assetTable = Table.aliased("tb_asset", "asset");

    public ReferencedAssetRepositoryCustomImpl(R2dbcEntityTemplate template,
                                               EntityManager entityManager,
                                               ReferencedAssetRowMapper referencedAssetRowMapper,
                                               NewsArticleRowMapper newsArticleRowMapper,
                                               AssetRowMapper assetRowMapper) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.referencedAssetRowMapper = referencedAssetRowMapper;
        this.newsArticleRowMapper = newsArticleRowMapper;
        this.assetRowMapper = assetRowMapper;
    }

    @Override
    public Flux<ReferencedAsset> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<ReferencedAsset> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    @Override
    public Flux<ReferencedAsset> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<ReferencedAsset> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    @Override
    public Flux<ReferencedAsset> findByArticleId(String articleId) {
        return createQuery(null, where("article_id").is(articleId)).all();
    }

    @Override
    public Flux<ReferencedAsset> findByAssetURL(String assetURL) {
        return createQuery(null, where("asset_url").is(assetURL)).all();
    }

    @Override
    public Mono<ReferencedAsset> findByArticleIdAndAssetURL(String articleId, String assetURL) {
        return createQuery(null,
                           where("asset_url").is(assetURL)
                            .and("article_id").is(articleId))
                           .one();
    }

    RowsFetchSpec<ReferencedAsset> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = ReferencedAssetSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(NewsArticleSqlHelper.getColumns(newsArticleTable, "article"));
        columns.addAll(AssetSqlHelper.getColumns(assetTable, "asset"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(newsArticleTable)
            .on(Column.create("article_id", entityTable))
            .equals(Column.create("id", newsArticleTable))
            .leftOuterJoin(assetTable)
            .on(Column.create("asset_url", entityTable))
            .equals(Column.create("ds_url", assetTable));

        String select = entityManager.createSelect(selectFrom, ReferencedAsset.class, pageable, criteria);
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

    private ReferencedAsset process(Row row, RowMetadata metadata) {
        ReferencedAsset entity = referencedAssetRowMapper.apply(row, "e");
        entity.setNewsArticle(newsArticleRowMapper.apply(row, "article"));
        entity.setAssetReferenced(assetRowMapper.apply(row, "asset"));
        return entity;
    }

    @Override
    public <S extends ReferencedAsset> Mono<S> save(S entity) {

        /*
        return this.findById(entity.getId())
                   .switchIfEmpty(insert(entity))
                   .then(update(entity)).thenReturn(entity);
        */

        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return updateIntReturn(entity)
                    .map(
                            numberOfUpdates -> {
                                if (numberOfUpdates.intValue() <= 0) {
                                    throw new IllegalStateException("Unable to update ReferencedAsset with id = " + entity.getId());
                                }
                                return entity;
                            }
                    );

        }

    }

    @Override
    public <S extends ReferencedAsset> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    public Mono<Integer> updateIntReturn(ReferencedAsset entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }

    @Override
    public <S extends ReferencedAsset> Mono<S> update(S entity) {
        return r2dbcEntityTemplate.update(entity);
    }

}

class ReferencedAssetSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();

        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("article_id", table, columnPrefix + "_article_id"));
        columns.add(Column.aliased("asset_url", table, columnPrefix + "_asset_url"));
        columns.add(Column.aliased("dt_reference_start", table, columnPrefix + "_dt_reference_start"));
        columns.add(Column.aliased("dt_reference_end", table, columnPrefix + "_dt_reference_end"));
        columns.add(Column.aliased("qt_views", table, columnPrefix + "_qt_views"));

        return columns;

    }
}
