CREATE TABLE IF NOT EXISTS tb_author (
    id VARCHAR(10) PRIMARY KEY,
    ds_name VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_news_article (
    id VARCHAR(60) PRIMARY KEY,
    ds_title VARCHAR(255) NOT NULL,
    tx_article VARCHAR(4000),
    is_published BOOLEAN DEFAULT FALSE,
    dt_publishing TIMESTAMP,
    author_id VARCHAR(10) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES tb_author(id)
);

CREATE TABLE IF NOT EXISTS tb_asset (
    ds_url VARCHAR(512) PRIMARY KEY,
    tp_asset VARCHAR(10) NOT NULL,
    ds_caption VARCHAR(255),
    vl_publishing_price DECIMAL(9, 2),
    st_payment VARCHAR(15) DEFAULT 'NOT_PAID',
    dt_payment TIMESTAMP,
    author_id VARCHAR(10) NOT NULL,
    ds_mime_type VARCHAR(20),
    bl_content BLOB,
    FOREIGN KEY (author_id) REFERENCES tb_author(id)
);

CREATE TABLE IF NOT EXISTS tb_referenced_asset (
    id INT AUTO_INCREMENT PRIMARY KEY,
    article_id VARCHAR(60) NOT NULL ,
    asset_url VARCHAR(512) NOT NULL ,
    dt_reference_start TIMESTAMP,
    dt_reference_end TIMESTAMP,
    qt_views INT DEFAULT 0,
    FOREIGN KEY (article_id) REFERENCES tb_news_article(id),
    FOREIGN KEY (asset_url) REFERENCES tb_asset(ds_url),
    UNIQUE (article_id, asset_url)
);
