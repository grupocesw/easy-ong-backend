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
(2, 'MANAGER'),
(3, 'USER');

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

INSERT INTO faqs (id, question, answer) VALUES
(1, 'Vocês tem vinculos com alguma ONG?', 'Não, somos totalmente independentes.'),
(2, 'O app é gratuito?', 'Sim, o app é totalmente gratuíto.'),
(3, 'Tem opção para IOS?', 'Somente para plataforma android inicialmente.'),
(4, 'É possível logar com minhas redes sociais?', 'Sim, o app possuí essa funcionalidade que agiliza o cadastro.');

