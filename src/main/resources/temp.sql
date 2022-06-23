create schema cloud_service;
create table cloud_service.users
(
    id       bigserial
        constraint users_pk
            primary key,
    emale    varchar not null,
    password varchar not null
);

create unique index users_emale_uindex
    on cloud_service.users (emale);
