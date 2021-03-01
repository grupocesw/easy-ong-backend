INSERT INTO address (id, number,  complement, latitude, longitude, street_id) VALUES
(1, 19, 'Bloco C APTO 22', '37.4267861', '-122.0806032', 1),
(2, 365, '', '', '', 2),
(3, 876, '', '', '', 3);

INSERT INTO picture (id, name) VALUES
(1, '1_ong.jpeg'),
(2, '2_ong.png'),
(3, '3_ong.jpg'),
(4, '4_user.jpg');

INSERT INTO ngo (id, name, cnpj, description, activated, address_id, created_at, updated_at) VALUES
(1, 'Cantinho Feliz', '59300268000191', 'Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero', 1, 1, '2021-01-01 00:00:00', '2021-01-01 00:00:00');

INSERT INTO ngo (id, name, cnpj, description, activated, address_id, created_at, updated_at) VALUES
(2, 'Ong Bom de Bola', '26809488000196', 'Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero', 1, 2, '2021-01-01 00:00:00', '2021-01-01 00:00:00');

INSERT INTO ngo (id, name, cnpj, description, activated, address_id, created_at, updated_at) VALUES
(3, 'Ong Green Side', '52851775000174', 'Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero', 1, 3, '2021-01-01 00:00:00', '2021-01-01 00:00:00');

INSERT INTO more_information_ngo (id, information, ngo_id) VALUES
(1, 'No ano de 2020 foram doadas 800 cestas básicas.', 1),
(2, 'Somos mais de 40 voluntários.', 1),
(3, 'Foram arrecadados mais de R$50000 em 2020.', 1);

INSERT INTO contact (id, type, content) VALUES
(1, 0, 'cantinho.feliz@gmail.com'),
(2, 9, '11954563652'),
(3, 8, 'https://cantinhofeliz.com.br'),
(4, 4, '112223333'),
(5, 7, '@cantinhofeliz2020');

INSERT INTO ngo_social_cause (ngo_id, sociaL_cause_id) VALUES
(1, 2),
(1, 8),
(1, 6),
(1, 5),
(2, 1),
(2, 5),
(3, 6);

INSERT INTO ngo_picture (ngo_id, picture_id) VALUES
(1, 1),
(1, 2),
(1, 3);

INSERT INTO ngo_contact (ngo_id, contact_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5);

INSERT INTO user (id, name, birthday, gender, username, password, picture_id, created_at, updated_at, checked_at) VALUES
(1, 'Astrobilobaldo Paixão', '1990-09-22', 0, 'astrobilobaldo@test.com',  '12345678', 4, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(2, 'Biri Jean', '1983-07-23', 0, 'birijean@test.com',  '12345678', null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(3, 'Paul Stronger', '1998-05-04', 0, 'paulstronger@test.com',  '12345678', null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(4, 'Sander Jay', '1996-11-29', 0, 'sanderjay@test.com',  '12345678', null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(5, 'Juma Haskovo', '2000-06-07', 1, 'jumahaskov@test.com',  '12345678', null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(6, 'Mirna Kyle', '1967-03-09', 1, 'mirnakyle@test.com',  '12345678', null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(7, 'Tricia Marcos', '1984-12-25', 2, 'triciamarcos@test.com',  '12345678', null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00');

INSERT INTO user_social_cause (user_id, social_cause_id) VALUES
(1, 6),
(1, 2),
(2, 3),
(3, 4),
(4, 6);

INSERT INTO user_favorite_ngo (user_id, ngo_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3),
(4, 1);