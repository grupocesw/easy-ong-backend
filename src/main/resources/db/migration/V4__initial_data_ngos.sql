INSERT INTO addresses (id, title, description, street, number, complement, zip_code, latitude, longitude, district, city_id) VALUES
(1, 'Associação Apoio Meninas Meninos Região Sé', 'Clínica de Reabilitação', 'Avenida Angélica', '19', 'Próximo ao metrô Santa Cecília', '01227000', '37.4267861', '-122.0806032', 'Santa Cecília', 1),
(2, '', '', 'Rua Paulo Carneiro', '30', '', '02442090', '', '', 'Lauzane Paulista', 1),
(3, '', '', 'Rua Djalma Dutra', '70', 'COMPLEMENT', '01103010', '', '', 'Luz', 1),
(4, '', '', 'Rua iguaçu', '132', '', '01107050', '', '', 'Luz', 1),
(5, '', '', 'Rua Maria Luisa de Pinho', '30', '', '03124090', '', '', 'Moóca', 1),
(6, '', '', 'R. Cruz de Malta', '765', '', '02248001', '', '', 'Parada Inglesa', 1),
(7, '', '', 'Rua Maestro Cardim', '560', 'Conjunto 215', '01323000', '', '', 'Paraíso', 1)
--(ID, 'TITLE', 'DESCRIPTION', 'STREET', 'NUMBER', 'COMPLEMENT', 'ZIP_CODE', '', '', 'DISTRICT', 1),
;

INSERT INTO pictures (id, url) VALUES
(4, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpyIEzorvdG9JxApOvumLOTVxVurw1q1FQEw&usqp=CAU'),
(5, 'http://cecosi.org.br/upload/eyJpdiI6IjBJNURcL2pJV1ZFZXFGUWFiV3VQR0lnPT0iLCJ2YWx1ZSI6Imsyejdua25oZ2lZZ2dhNHFlNlREZnc9PSIsIm1hYyI6IjJlZWFhMTYyNjVmYWU4N2NhNTViMzNjNTY5ZGM5MDI0NzFjZGM1YzFhNzY4ZTk0N2Y0MjI3ZjNjODYyNDA1ZTIifQ==.jpg'),
(6, 'https://scontent.fcgh14-1.fna.fbcdn.net/v/t1.6435-9/41691147_2352245718126062_1432030963654721536_n.png?_nc_cat=101&ccb=1-3&_nc_sid=09cbfe&_nc_ohc=KxAstR8u8AIAX-Wsyqi&_nc_oc=AQkPQ-8pcKM6zf_5FmVZ00tIrFoea0YMGFuZrOMWSmaDxjSOgu71z_BqEmtc2EGdNlWPbXKSdEPB2T2e7mlv_VNU&_nc_ht=scontent.fcgh14-1.fna&oh=b3ea4e4811c6f61d365591b9ee7d543d&oe=60C05CC7'),
(7, 'http://www.uropediatria.com.br/wp-content/uploads/2019/04/Doutores.png'),
(8, 'http://www.uropediatria.com.br/wp-content/uploads/2019/03/da0aba37422a-1.jpg'),
(9, 'http://www.uropediatria.com.br/wp-content/uploads/2019/03/6f2559d0fe8.jpg'),
(10, 'http://www.uropediatria.com.br/wp-content/uploads/2019/03/783e3214a07b-1.jpg'),
(11, 'http://cecosi.org.br/images/logo-cecosi.png'),
(12, 'https://inkinspira.com.br/wp-content/uploads/2017/02/MAIORES-ONGS-DO-BRASIL.jpg'),
(13, 'https://lh3.googleusercontent.com/proxy/CyxHMDNBxPzJyTsnEbomc4h3BmmUSVvWfD5Flsso639yxRNcEcDYTvlqeI4xLqOpuqbUvE8H89cD_rUO809WzMySJ_qXhGehIQuNfjP3S9bsUP6J'),
(14, 'http://s2.glbimg.com/zHTIn5PVeAlwcHoIfdO3qMZ2QDTXRJMl3UBBE20cSNNIoz-HdGixxa_8qOZvMp3w/s.glbimg.com/og/rg/f/original/2012/11/22/educap_291x388.jpg.jpg'),
(15, 'https://publicitariopoa.files.wordpress.com/2009/10/logo-educa-tche.jpg'),
(16, 'https://static.wixstatic.com/media/90cf78_4e120fdf97bf4e5c9a591eca8337279b~mv2.jpg/v1/fill/w_395,h_254,al_c,lg_1,q_80/90cf78_4e120fdf97bf4e5c9a591eca8337279b~mv2.webp'),
(17, 'https://i.pinimg.com/236x/7c/c5/bb/7cc5bbd7ebf2ca86b4a168d564e165b2.jpg'),
(18, 'https://catracalivre.com.br/wp-content/uploads/2013/10/quemsomos.jpg'),
(19, 'https://medias.cnnbrasil.com.br/google-removeu-anuncios-que-desrespeitavam-sua-politica-de-distorcao.jpeg?format=JPEG&image=https://mediastorage.cnnbrasil.com.br/IMAGES/00/00/00/8099_49A09089147DB65C.jpg&width=804&height=452&resize=CROP'),
(20, 'https://www.techsoupbrasil.org.br/sites/default/files/images/93bb727d-9a49-4922-bbfc-9abe6dadf5b3.jpeg'),
(21, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9N2OJxSxQ4RzMeE0Cn3ttfqk2R4y-wssQ1Q&usqp=CAU'),
(22, 'https://www.opresente.com.br/wp-content/uploads/2019/04/ongs-606x341.png'),
(23, 'https://nossacausa.com/wp-content/uploads/2017/08/melhores-ongs.jpg')
--(ID, 'URL'),
;

INSERT INTO ngos (id, name, cnpj, description, address_id) VALUES
(1, 'Cantinho Feliz', '59300268000191', 'Esta é a descrição da ONG Cantinho Feliz', 1),
(2, 'Centro Comunitário Santa Inês - CECOSI', '51601748000180', 'Promover e incentivar a assistência social, a educação e a cultura visando o pleno desenvolvimento da pessoa', 2),
(3, 'ASSOCIAÇÃO DE APOIO ÀS MENINAS E MENINOS DA REGIÃO SÉ - AACriança', '74121880000190', 'Somos uma organização da sociedade civil que atua junto às crianças e adolescentes em situação de rua e em situação de vulnerabilidade social', 3),
(4, 'RESIDÊNCIA PARA PESSOAS PORTADORAS DE HIV', '05424674000137', '', 4),
(5, 'Capes -Centro de Apoio Profissionalizante Educacional Social', '06346995000123', '', 5),
(6, 'Instituto De Empreendedores Ambientais E Sociais', '08404742000194', '', 6),
(7, 'Centro De Apoio À Criança Com Anomalia Urológica', '07862926000135', 'As cirurgias são realizadas preferencialmente nos Hospitais Albert Einstein e Hospital Leforte. Consulte-nos sobre a possibilidade de realizar em outros hospitais.', 7)
--(ID, 'NAME', 'CNPJ', 'DESCRIPTION', ADDRESS_ID),
;

INSERT INTO ngo_more_informations (id, information, ngo_id) VALUES
(1, 'No ano de 2020 foram doadas 800 cestas básicas.', 1),
(2, 'Somos mais de 40 voluntários.', 1),
(3, 'Foram arrecadados mais de R$50000 em 2020.', 1);

INSERT INTO contacts (id, type, content) VALUES
(1, 0, 'cantinho.feliz@gmail.com'),
(2, 9, '(11) 95456-3652'),
(3, 8, 'https://cantinhofeliz.com.br'),
(4, 4, '(11) 2222-3333'),
(5, 7, '@cantinhofeliz2020'),
(6, 0, 'cecosi@globo.com'),
(7, 9, '(11) 2232-5973'),
(8, 8, 'http://cecosi.org.br/'),
(9, 9, '(11) 3229-3935'),
(10, 0, 'aacrianca@uol.com.br'),
(11, 8, 'http://www.aacrianca.com.br/'),
(12, 1, 'https://www.facebook.com/cedeca.se/'),
(13, 0, 'michellyalves@uol.com.br'),
(14, 8, 'http://www.larsomandoforcas.org/'),
(15, 0, 'consultoria010203@gmail.com'),
(16, 8, 'http://www.capes.org.br/'),
(17, 8, 'http://www.uropediatria.com.br/')
--(#, TYPE, 'CONTENT'),
;

INSERT INTO ngo_contacts (ngo_id, contact_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(2, 6),
(2, 7),
(2, 8),
(3, 9),
(3, 11),
(3, 12),
(4, 13),
(4, 14),
(5, 15),
(6, 16),
(7, 17)
--(NGO_ID, CONTACT_ID),
;

INSERT INTO ngo_social_causes (ngo_id, social_cause_id) VALUES
(1, 2),
(1, 8),
(1, 6),
(1, 5),
(2, 1),
(3, 6),
(3, 9),
(4, 8),
(5, 1),
(6, 5),
(6, 10),
(7, 9)
--(NGO_ID, CAUSE),
;

INSERT INTO ngo_pictures (ngo_id, picture_id) VALUES
(1, 20),
(1, 21),
(1, 23),
(2, 11),
(2, 5),
(2, 22),
(3, 6),
(4, 16),
(5, 12),
(5, 15),
(6, 14),
(7, 7),
(7, 8),
(7, 9),
(7, 10)
--(NGO_ID, PICTURE_ID),
;

