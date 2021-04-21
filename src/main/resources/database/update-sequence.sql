SELECT setval(pg_get_serial_sequence('addresses', 'id'), MAX(id)) FROM addresses;
SELECT setval(pg_get_serial_sequence('cities', 'id'), MAX(id)) FROM cities;
SELECT setval(pg_get_serial_sequence('contacts', 'id'), MAX(id)) FROM contacts;
SELECT setval(pg_get_serial_sequence('countries', 'id'), MAX(id)) FROM countries;
SELECT setval(pg_get_serial_sequence('districts', 'id'), MAX(id)) FROM districts;
SELECT setval(pg_get_serial_sequence('frequently_asked_questions', 'id'), MAX(id)) FROM frequently_asked_questions;
SELECT setval(pg_get_serial_sequence('ngo_suggestions', 'id'), MAX(id)) FROM ngo_suggestions;
SELECT setval(pg_get_serial_sequence('ngo_more_informations', 'id'), MAX(id)) FROM ngo_more_informations;
SELECT setval(pg_get_serial_sequence('ngos', 'id'), MAX(id)) FROM ngos;
SELECT setval(pg_get_serial_sequence('notifications', 'id'), MAX(id)) FROM notifications;
SELECT setval(pg_get_serial_sequence('pictures', 'id'), MAX(id)) FROM pictures;
SELECT setval(pg_get_serial_sequence('roles', 'id'), MAX(id)) FROM roles;
SELECT setval(pg_get_serial_sequence('social_causes', 'id'), MAX(id)) FROM social_causes;
SELECT setval(pg_get_serial_sequence('states', 'id'), MAX(id)) FROM states;
SELECT setval(pg_get_serial_sequence('streets', 'id'), MAX(id)) FROM streets;
SELECT setval(pg_get_serial_sequence('people', 'id'), MAX(id)) FROM people;
SELECT setval(pg_get_serial_sequence('users', 'id'), MAX(id)) FROM users;
SELECT setval(pg_get_serial_sequence('zip_codes', 'id'), MAX(id)) FROM zip_codes;

