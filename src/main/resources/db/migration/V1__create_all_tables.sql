create sequence confirmation_token_sequence start 1 increment 1;

    create table addresses (
       id  bigserial not null,
        complement varchar(255),
        description varchar(255) not null,
        district varchar(255) not null,
        latitude varchar(255),
        longitude varchar(255),
        number varchar(7) not null,
        street varchar(255) not null,
        title varchar(255) not null,
        zip_code varchar(8),
        city_id int8,
        primary key (id)
    );

    create table app_contacts (
       id  bigserial not null,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        email varchar(100),
        message TEXT not null,
        name varchar(100),
        reason int4,
        user_id int8,
        primary key (id)
    );

    create table cities (
       id  bigserial not null,
        name varchar(255) not null,
        state_id int8,
        primary key (id)
    );

    create table confirmation_tokens (
       id int8 not null,
        confirmed_at timestamp,
        created_at timestamp not null,
        expires_at timestamp not null,
        token varchar(255) not null,
        user_id int8 not null,
        primary key (id)
    );

    create table contacts (
       id  bigserial not null,
        content varchar(255) not null,
        type int4 not null,
        primary key (id)
    );

    create table countries (
       id  bigserial not null,
        abbreviation varchar(3) not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table faqs (
       id  bigserial not null,
        answer TEXT not null,
        question TEXT not null,
        primary key (id)
    );

    create table ngo_contacts (
       ngo_id int8 not null,
        contact_id int8 not null,
        primary key (ngo_id, contact_id)
    );

    create table ngo_more_informations (
       id  bigserial not null,
        information TEXT,
        ngo_id int8,
        primary key (id)
    );

    create table ngo_pictures (
       ngo_id int8 not null,
        picture_id int8 not null,
        primary key (ngo_id, picture_id)
    );

    create table ngo_social_causes (
       ngo_id int8 not null,
        social_cause_id int8 not null,
        primary key (ngo_id, social_cause_id)
    );

    create table ngos (
       id  bigserial not null,
        activated BOOLEAN DEFAULT true not null,
        cnpj varchar(14) not null,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        description TEXT,
        name varchar(100) not null,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        address_id int8,
        primary key (id)
    );

    create table notification_users (
       notification_id int8 not null,
        user_id int8 not null,
        primary key (notification_id, user_id)
    );

    create table notifications (
       id  bigserial not null,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        description TEXT,
        title varchar(100) not null,
        type int4 not null,
        primary key (id)
    );

    create table people (
       id  bigserial not null,
        birthday date,
        gender int4,
        name varchar(100) not null,
        primary key (id)
    );

    create table pictures (
       id  bigserial not null,
        url TEXT not null,
        primary key (id)
    );

    create table roles (
       id  bigserial not null,
        name varchar(100) not null,
        primary key (id)
    );

    create table social_causes (
       id  bigserial not null,
        name varchar(100) not null,
        primary key (id)
    );

    create table states (
       id  bigserial not null,
        abbreviation varchar(2) not null,
        name varchar(255) not null,
        country_id int8,
        primary key (id)
    );

    create table user_favorite_ngos (
       user_id int8 not null,
        ngo_id int8 not null,
        primary key (user_id, ngo_id)
    );

    create table user_roles (
       user_id int8 not null,
        role_id int8 not null,
        primary key (user_id, role_id)
    );

    create table user_social_causes (
       user_id int8 not null,
        social_cause_id int8 not null,
        primary key (user_id, social_cause_id)
    );

    create table users (
       id  bigserial not null,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        enabled boolean,
        locked boolean,
        password varchar(100) not null,
        provider varchar(255) not null,
        provider_id varchar(255),
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        username varchar(100) not null,
        person_id int8,
        picture_id int8,
        primary key (id)
    );

    alter table ngos
       add constraint UK_5ltyk2kh9aqwtdmweyyeayj5c unique (cnpj);

    alter table users
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

    alter table addresses
       add constraint FK9fkb8qaj71tiyr9htkmn7r8y5
       foreign key (city_id)
       references cities;

    alter table app_contacts
       add constraint FKpnc1mryfgnh0dx0j1w461ygex
       foreign key (user_id)
       references users
       on delete cascade;

    alter table cities
       add constraint FKsu54e1tlhaof4oklvv7uphsli
       foreign key (state_id)
       references states;

    alter table confirmation_tokens
       add constraint FKhpuw37a1pqxfb6ya1nv5lm4ga
       foreign key (user_id)
       references users
       on delete cascade;

    alter table ngo_contacts
       add constraint FKf3n40noaya6w31bv4n5sguf47
       foreign key (contact_id)
       references contacts
       on delete cascade;

    alter table ngo_contacts
       add constraint FKa8rj3kotuf5i60jncnyw43jka
       foreign key (ngo_id)
       references ngos;

    alter table ngo_more_informations
       add constraint FK8a5ouqphl111a0ue4ulkef1ml
       foreign key (ngo_id)
       references ngos
       on delete cascade;

    alter table ngo_pictures
       add constraint FKpsjgmrcwc0t4qo6g3j0ivok7y
       foreign key (picture_id)
       references pictures
       on delete cascade;

    alter table ngo_pictures
       add constraint FK2mveoidm3siprmjv8cpqggiwo
       foreign key (ngo_id)
       references ngos;

    alter table ngo_social_causes
       add constraint FK83i2ypim2hetyqenmwutw17fp
       foreign key (social_cause_id)
       references social_causes
       on delete cascade;

    alter table ngo_social_causes
       add constraint FKd6siimwpl22ljtx33hovxtcd
       foreign key (ngo_id)
       references ngos;

    alter table ngos
       add constraint FKsi2u1hokw8jnkngjood0v2gmh
       foreign key (address_id)
       references addresses;

    alter table notification_users
       add constraint FK2t7gew0g9cmcmtimfjvh4h85j
       foreign key (user_id)
       references users
       on delete cascade;

    alter table notification_users
       add constraint FKg8k54m3hj2oj4cal9ekcd6cuo
       foreign key (notification_id)
       references notifications;

    alter table states
       add constraint FKskkdphjml9vjlrqn4m5hi251y
       foreign key (country_id)
       references countries;

    alter table user_favorite_ngos
       add constraint FK5sumkl57mi1m8wff3vmhgljf4
       foreign key (ngo_id)
       references ngos
       on delete cascade;

    alter table user_favorite_ngos
       add constraint FKq9hghbrucveb5797kii4f02nx
       foreign key (user_id)
       references users;

    alter table user_roles
       add constraint FKh8ciramu9cc9q3qcqiv4ue8a6
       foreign key (role_id)
       references roles
       on delete cascade;

    alter table user_roles
       add constraint FKhfh9dx7w3ubf1co1vdev94g3f
       foreign key (user_id)
       references users;

    alter table user_social_causes
       add constraint FKinuwsh00e83hchul6agqiuv8s
       foreign key (social_cause_id)
       references social_causes
       on delete cascade;

    alter table user_social_causes
       add constraint FK11j68giraaqyy4gdrid1j5l83
       foreign key (user_id)
       references users;

    alter table users
       add constraint FKsv7wp99d6g5x8iisfpjf6sbpg
       foreign key (person_id)
       references people;

    alter table users
       add constraint FK16449k08lp0f50csh6jlb5g2c
       foreign key (picture_id)
       references pictures;
