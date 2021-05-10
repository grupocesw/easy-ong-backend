INSERT INTO pictures (id, url) VALUES
(4, '4.jpg');

INSERT INTO people (id, name, birthday, gender) VALUES
(1, 'Administrator', '2000-01-01', 2),
(2, 'Andrê Reges', '1990-09-22', 0);

INSERT INTO users (id, username, password, locked, enabled, person_id, picture_id) VALUES
(1, 'admin@easyong.com.br', '$2a$10$8ipYZW1YCciTvy57AA91sumd6E9NiSwRPXeSFp9AxIqRg949bct5e', false, true, 1, null),
(2, 'andreriggs90@gmail.com', '$2a$10$RfwrevwuOvdxyw8v7x1eN.AhJbE7V4g/rhuPeEhiOujIgxvNmA.HK', false, true, 2, 4);

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 3);

INSERT INTO user_social_causes (user_id, social_cause_id) VALUES
(2, 6),
(2, 2);

INSERT INTO user_favorite_ngos (user_id, ngo_id) VALUES
(2, 1);


