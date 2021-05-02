package de.futurecompany.repositories.customImpls;

import de.futurecompany.models.Asset;
import de.futurecompany.models.NewsArticle;
import de.futurecompany.repositories.AssetRepositoryCustom;
import de.futurecompany.repositories.helpers.EntityManager;
import de.futurecompany.repositories.rowmappers.AssetRowMapper;
import de.futurecompany.repositories.rowmappers.AuthorRowMapper;
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

public class AssetRepositoryCustomImpl implements AssetRepositoryCustom {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final AuthorRowMapper authorRowMapper;
    private final AssetRowMapper assetRowMapper;

    private static final Table entityTable = Table.aliased("tb_asset", EntityManager.ENTITY_ALIAS);
    private static final Table authorTable = Table.aliased("tb_author", "asset_author");

    public AssetRepositoryCustomImpl(R2dbcEntityTemplate template,
                                     EntityManager entityManager,
                                     AuthorRowMapper authorMapper,
                                     AssetRowMapper assetRowMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.authorRowMapper = authorMapper;
        this.assetRowMapper = assetRowMapper;
    }

    @Override
    public Flux<Asset> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<Asset> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<Asset> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = AssetSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(AuthorSqlHelper.getColumns(authorTable, "asset_author"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(authorTable)
            .on(Column.create("author_id", entityTable))
            .equals(Column.create("id", authorTable));

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
    public Flux<Asset> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<Asset> findById(String id) {
        return createQuery(null, where("ds_url").is(id)).one();
    }

    @Override
    public Flux<Asset> findByAuthorIdImpl(String authorId) {
        return createQuery(null, where("author_id").is(authorId)).all();
    }

    private Asset process(Row row, RowMetadata metadata) {
        Asset entity = assetRowMapper.apply(row, "e");
        entity.setAuthor(authorRowMapper.apply(row, "asset_author"));
        return entity;
    }


    @Override
    public <S extends Asset> Mono<S> save(S entity) {

        return this.findById(entity.getUrl())
                   .switchIfEmpty(insert(entity))
                   .then(update(entity)).thenReturn(entity);
    }

    @Override
    public <S extends Asset> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends Asset> Mono<S> update(S entity) {
        return r2dbcEntityTemplate.update(entity);
    }

}

class AssetSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();

        columns.add(Column.aliased("ds_url", table, columnPrefix + "_ds_url"));
        columns.add(Column.aliased("tp_asset", table, columnPrefix + "_tp_asset"));
        columns.add(Column.aliased("ds_caption", table, columnPrefix + "_ds_caption"));
        columns.add(Column.aliased("vl_publishing_price", table, columnPrefix + "_vl_publishing_price"));
        columns.add(Column.aliased("st_payment", table, columnPrefix + "_st_payment"));
        columns.add(Column.aliased("dt_payment", table, columnPrefix + "_dt_payment"));
        columns.add(Column.aliased("author_id", table, columnPrefix + "_author_id"));
        columns.add(Column.aliased("ds_mime_type", table, columnPrefix + "_ds_mime_type"));
        columns.add(Column.aliased("bl_content", table, columnPrefix + "_bl_content"));

        return columns;

    }
}
