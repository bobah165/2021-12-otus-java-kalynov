create table phone
(
    id bigserial not null primary key,
    number varchar(50),
    client_id bigint
);

create table address
(
    id bigserial not null primary key,
    street varchar(50)
);

ALTER TABLE phone
ADD CONSTRAINT phone_client_id_fk
FOREIGN KEY (client_id) REFERENCES client10;

ALTER TABLE client10
ADD CONSTRAINT client_address_id_fk
FOREIGN KEY (address_id) REFERENCES address;


