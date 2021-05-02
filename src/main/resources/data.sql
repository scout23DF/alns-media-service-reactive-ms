INSERT INTO tb_author (id, ds_name) VALUES
  ('author01', 'Aqeel Cox'),
  ('author02', 'Ernie Rudd'),
  ('author03', 'Ishan Plant'),
  ('author04', 'Morgan Noble'),
  ('author05', 'Greg Barclay'),
  ('author06', 'Liyana Turner'),
  ('author07', 'Myles Malone'),
  ('author08', 'Matilda Wagner'),
  ('author09', 'Marcia Clay');


INSERT INTO tb_news_article (id, ds_title, tx_article, is_published, dt_publishing, author_id) VALUES
   ('article001', 'Fora Bolsonaro #1', 'Fora Bolsonaro | Fora Bolsonaro | Fora Bolsonaro', FALSE, NULL, 'author01')
  ,('article002', 'Fora Bolsonaro #2', 'Fora Bolsonaro | Fora Bolsonaro | Fora Bolsonaro', FALSE, NULL, 'author04')
  ,('article003', 'Fora Bolsonaro #3', 'Fora Bolsonaro | Fora Bolsonaro | Fora Bolsonaro', FALSE, NULL, 'author04')
;
