-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
create table client
(
    id   bigserial not null primary key,
    name varchar(50),
    password varchar(50)
);


-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
--create sequence client_sequence start with 1 increment by 1;
--
--create table client10
--(
--    id   bigint not null primary key,
--    name varchar(50)
--);
