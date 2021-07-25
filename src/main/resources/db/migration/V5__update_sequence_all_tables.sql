SELECT setval(pg_get_serial_sequence('addresses', 'id'), MAX(id)) FROM addresses;
SELECT setval(pg_get_serial_sequence('app_contacts', 'id'), MAX(id)) FROM app_contacts;
SELECT setval(pg_get_serial_sequence('cities', 'id'), MAX(id)) FROM cities;
SELECT setval(pg_get_serial_sequence('confirmation_tokens', 'id'), MAX(id)) FROM confirmation_tokens;
SELECT setval(pg_get_serial_sequence('contacts', 'id'), MAX(id)) FROM contacts;
SELECT setval(pg_get_serial_sequence('countries', 'id'), MAX(id)) FROM countries;
SELECT setval(pg_get_serial_sequence('faqs', 'id'), MAX(id)) FROM faqs;
SELECT setval(pg_get_serial_sequence('ngo_more_informations', 'id'), MAX(id)) FROM ngo_more_informations;
SELECT setval(pg_get_serial_sequence('ngos', 'id'), MAX(id)) FROM ngos;
SELECT setval(pg_get_serial_sequence('notifications', 'id'), MAX(id)) FROM notifications;
SELECT setval(pg_get_serial_sequence('pictures', 'id'), MAX(id)) FROM pictures;
SELECT setval(pg_get_serial_sequence('roles', 'id'), MAX(id)) FROM roles;
SELECT setval(pg_get_serial_sequence('social_causes', 'id'), MAX(id)) FROM social_causes;
SELECT setval(pg_get_serial_sequence('states', 'id'), MAX(id)) FROM states;
SELECT setval(pg_get_serial_sequence('people', 'id'), MAX(id)) FROM people;
SELECT setval(pg_get_serial_sequence('users', 'id'), MAX(id)) FROM users;

