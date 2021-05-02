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

