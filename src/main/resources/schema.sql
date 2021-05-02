DROP TABLE IF EXISTS tb_asset;
DROP TABLE IF EXISTS tb_news_article;
DROP TABLE IF EXISTS tb_author;

CREATE TABLE IF NOT EXISTS tb_author (
    id VARCHAR(10) PRIMARY KEY,
    ds_name VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_news_article (
    id VARCHAR(60) PRIMARY KEY,
    ds_title VARCHAR(255) NOT NULL,
    tx_article VARCHAR(4000),
    is_published BOOLEAN DEFAULT FALSE,
    dt_publishing DATETIME,
    author_id VARCHAR(10) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES tb_author(id)
);

CREATE TABLE IF NOT EXISTS tb_asset (
   ds_url VARCHAR(512) PRIMARY KEY,
   tp_asset VARCHAR(10) NOT NULL,
   ds_caption VARCHAR(255),
   vl_publishing_price DECIMAL(9, 2),
   st_payment VARCHAR(15) DEFAULT 'NOT_PAID',
   dt_payment DATETIME,
   author_id VARCHAR(10) NOT NULL,
   ds_mime_type VARCHAR(20),
   bl_content BLOB,
   FOREIGN KEY (author_id) REFERENCES tb_author(id)
);

