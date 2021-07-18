INSERT INTO pictures (id, url) VALUES
(2, 'admin.png'),
(3, '2.jpg')
;

INSERT INTO people (id, name, birthday, gender) VALUES
(1, 'Administrator', '2000-01-01', 2),
(2, 'Andrê Reges', '1990-09-22', 0);

INSERT INTO users (id, username, password, locked, enabled, person_id, picture_id, provider, provider_id) VALUES
(1, 'admin@easyong.com.br', '$2a$10$up1BhfcZea4NRtiOM4Dcr.aFhwSp3vCrQatvrpN48wlryNQvtfMci', false, true, 1, 2, 'local', null),
(2, 'andreriggs90@gmail.com', '$2a$10$z0lTSRrkdisqz/sgfxXOCOYI3/W4a6daB0c6tNOq1Bt9QKkmnakOi', false, true, 2, 3, 'local', null);

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 3);

INSERT INTO user_social_causes (user_id, social_cause_id) VALUES
(2, 6),
(2, 2);

INSERT INTO user_favorite_ngos (user_id, ngo_id) VALUES
(2, 1);


