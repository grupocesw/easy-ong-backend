INSERT INTO social_causes (id, name) VALUES
(1, 'Educação'),
(2, 'Proteção Animal'),
(3, 'Luta Contra as Drogas'),
(4, 'Esporte e Cultura'),
(5, 'Meio Ambiente'),
(6, 'Luta Contra Fome e Pobreza'),
(7, 'Saúde e Bem Estar'),
(8, 'Direitos Humanos'),
(9, 'Crianças'),
(10, 'Serviço Social'),
(11, 'Outros');

INSERT INTO roles (id, name) VALUES
(1, 'ADMIN'),
(2, 'USER');

INSERT INTO countries (id, name, abbreviation) VALUES
(1, 'Brasil', 'BRA');

INSERT INTO states (id, name, abbreviation, country_id) VALUES
(1, 'Alagoas', 'AL', 1),
(2, 'Amapá', 'AP', 1),
(3, 'Amazonas', 'AM', 1),
(4, 'Bahia', 'BA', 1),
(5, 'Ceará', 'CE', 1),
(6, 'Espírito Santo', 'ES', 1),
(8, 'Maranhão', 'MA', 1),
(9, 'Mato Grosso', 'MT', 1),
(10, 'Mato Grosso do Sul', 'MS', 1),
(11, 'Minas Gerais', 'MG', 1),
(12, 'Pará', 'PA', 1),
(13, 'Paraíba', 'PB', 1),
(14, 'Paraná', 'PR', 1),
(15, 'Pernambuco', 'PE', 1),
(16, 'Piauí', 'PI', 1),
(17, 'Rio de Janeiro', 'RJ', 1),
(18, 'Rio Grande do Norte', 'RN', 1),
(19, 'Rio Grande do Sul', 'RS', 1),
(20, 'Rondônia', 'RO', 1),
(21, 'Roraima', 'RR', 1),
(22, 'Santa Catarina', 'SC', 1),
(23, 'São Paulo', 'SP', 1),
(24, 'Sergipe', 'SE', 1),
(25, 'Tocantins', 'TO', 1),
(26, 'Distrito Federal', 'DF', 1);

INSERT INTO cities (id, name, state_id) VALUES
(1, 'São Paulo', 23);

INSERT INTO pictures (id, url) VALUES
(1, 'noImage.png'),
(2, 'admin.png')
;

INSERT INTO people (id, name, birthday, gender) VALUES
(1, 'Administrator', '2000-01-01', 2)
;

-- Default password: Admin@2021
INSERT INTO users (id, username, password, locked, enabled, person_id, picture_id, provider, provider_id) VALUES
(1, 'admin@easyong.com.br', '$2a$10$S98Kf2UDNGvpZc7hCiXFc.22KAsXNl44xmzS7.I2elHQJP3gjpkfG', false, true, 1, 2, 'local', null)
;

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1)
;