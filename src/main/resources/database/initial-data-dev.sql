INSERT INTO faqs (id, question, answer) VALUES
(1, 'Vocês tem vinculos com alguma ONG?', 'Não, somos totalmente independentes.'),
(2, 'O app é gratuito?', 'Sim, o app é totalmente gratuíto.'),
(3, 'Tem opção para IOS?', 'Somente para plataforma android inicialmente.'),
(4, 'É possível logar com minhas redes sociais?', 'Sim, o app possuí essa funcionalidade que agiliza o cadastro.');

INSERT INTO addresses (id, number,  complement, latitude, longitude, street_id) VALUES
(1, 19, 'Bloco C APTO 22', '37.4267861', '-122.0806032', 1),
(2, 365, '', '', '', 2),
(3, 876, '', '', '', 3);

INSERT INTO pictures (id, name) VALUES
(1, '1.jpeg'),
(2, '2.png'),
(3, '3.jpg'),
(4, '4.jpg');

INSERT INTO ngos (id, name, cnpj, description, activated, address_id) VALUES
(1, 'Cantinho Feliz', '59300268000191', 'Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero', true, 1),
(2, 'Ong Bom de Bola', '26809488000196', 'Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero', true, 2),
(3, 'Ong Green Side', '52851775000174', 'Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero', true, 3);

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
(1, 'Administrator', '2000-01-01', 2),
(2, 'Biri Jean', '1983-07-23', 0),
(3, 'Paul Stronger', '1998-05-04', 0),
(4, 'Sander Jay', '1996-11-29', 0),
(5, 'Juma Haskovo', '2000-06-07', 1),
(6, 'Mirna Kyle', '1967-03-09', 1),
(7, 'Tricia Marcos', '1984-12-25', 2);

INSERT INTO users (id, username, password, locked, enabled, person_id, picture_id) VALUES
(1, 'admin@easyong.com.br', '$2a$10$8ipYZW1YCciTvy57AA91sumd6E9NiSwRPXeSFp9AxIqRg949bct5e', false, true, 1, 4),
(2, 'birijean@easyong.com.br', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, true, 2, null),
(3, 'paulstronger@easyong.com.br', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, true, 3, null),
(4, 'sanderjay@easyong.com.br', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, true, 4, null),
(5, 'jumahaskov@easyong.com.br', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, true, 5, null),
(6, 'mirnakyle@easyong.com.br', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, true, 6, null),
(7, 'triciamarcos@easyong.com.br', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, true, 7, null);

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 3),
(3, 3),
(4, 3),
(5, 3),
(6, 3),
(7, 3);

INSERT INTO user_social_causes (user_id, social_cause_id) VALUES
(2, 6),
(2, 2),
(2, 3),
(3, 4),
(4, 6);

INSERT INTO user_favorite_ngos (user_id, ngo_id) VALUES
(2, 1),
(2, 3),
(2, 2),
(3, 3),
(4, 1);


