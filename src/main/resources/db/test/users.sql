INSERT INTO people (id, name, birthday, gender) VALUES
(2, 'Andrê Reges', '1990-09-22', 0)
;

INSERT INTO users (id, username, password, locked, enabled, person_id, picture_id, provider, provider_id) VALUES
(2, 'andreriggs90@gmail.com', '	$2a$10$l2Tq1/FyH/G8b6tDw6HkE.Hr6ZHFteA0kY6uXY5nY4/KN.qN2M67S', false, true, 2, 1, 'local', null)
;

INSERT INTO user_roles (user_id, role_id) VALUES
(2, 2)
;