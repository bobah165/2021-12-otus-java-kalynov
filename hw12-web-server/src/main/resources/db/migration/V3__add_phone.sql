create table phone
(
    id bigserial not null primary key,
    number varchar(50),
    client_id bigint
);

ALTER TABLE phone
ADD CONSTRAINT phone_client_id_fk
FOREIGN KEY (client_id) REFERENCES client;




