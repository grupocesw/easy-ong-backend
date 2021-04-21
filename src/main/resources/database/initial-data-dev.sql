INSERT INTO addresses (id, number,  complement, latitude, longitude, street_id) VALUES
(1, 19, 'Bloco C APTO 22', '37.4267861', '-122.0806032', 1),
(2, 365, '', '', '', 2),
(3, 876, '', '', '', 3);

INSERT INTO pictures (id, name) VALUES
(1, 'picture1.jpeg'),
(2, 'picture2.png'),
(3, 'picture3.jpg'),
(4, 'picture4.jpg');

INSERT INTO ngos (id, name, cnpj, description, activated, address_id, created_at, updated_at) VALUES
(1, 'Cantinho Feliz', '59300268000191', 'Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero', true, 1, '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(2, 'Ong Bom de Bola', '26809488000196', 'Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero', true, 2, '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(3, 'Ong Green Side', '52851775000174', 'Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero', true, 3, '2021-01-01 00:00:00', '2021-01-01 00:00:00');

INSERT INTO ngo_more_informations (id, information, ngo_id) VALUES
(1, 'No ano de 2020 foram doadas 800 cestas básicas.', 1),
(2, 'Somos mais de 40 voluntários.', 1),
(3, 'Foram arrecadados mais de R$50000 em 2020.', 1);

INSERT INTO contacts (id, type, content) VALUES
(1, 0, 'cantinho.feliz@gmail.com'),
(2, 9, '11954563652'),
(3, 8, 'https://cantinhofeliz.com.br'),
(4, 4, '112223333'),
(5, 7, '@cantinhofeliz2020');

INSERT INTO ngo_social_causes (ngo_id, sociaL_cause_id) VALUES
(1, 2),
(1, 8),
(1, 6),
(1, 5),
(2, 1),
(2, 5),
(3, 6);

INSERT INTO ngo_pictures (ngo_id, picture_id) VALUES
(1, 1),
(1, 2),
(1, 3);

INSERT INTO ngo_contacts (ngo_id, contact_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5);

INSERT INTO people (id, name, birthday, gender) VALUES
(1, 'Astrobilobaldo Paixão', '1990-09-22', 0),
(2, 'Biri Jean', '1983-07-23', 0),
(3, 'Paul Stronger', '1998-05-04', 0),
(4, 'Sander Jay', '1996-11-29', 0),
(5, 'Juma Haskovo', '2000-06-07', 1),
(6, 'Mirna Kyle', '1967-03-09', 1),
(7, 'Tricia Marcos', '1984-12-25', 2);

INSERT INTO users (id, username, password, locked, person_id, picture_id, created_at, updated_at, checked_at) VALUES
(1, 'astrobilobaldo@test.com', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, 1, 4, '2021-01-01 00:00:00', '2021-01-01 00:00:00', null),
(2, 'birijean@test.com', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, 2, null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(3, 'paulstronger@test.com', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, 3, null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(4, 'sanderjay@test.com', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, 4, null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(5, 'jumahaskov@test.com', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, 5, null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(6, 'mirnakyle@test.com', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, 6, null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
(7, 'triciamarcos@test.com', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, 7, null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00');

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 3),
(2, 3),
(3, 3),
(4, 3),
(5, 3),
(6, 3),
(7, 3);

INSERT INTO user_social_causes (user_id, social_cause_id) VALUES
(1, 6),
(1, 2),
(2, 3),
(3, 4),
(4, 6);

INSERT INTO user_favorite_ngos (user_id, ngo_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3),
(4, 1);


