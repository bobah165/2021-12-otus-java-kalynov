ALTER TABLE client ADD COLUMN address_id bigint;

ALTER TABLE client
ADD CONSTRAINT client_address_id_fk
FOREIGN KEY (address_id) REFERENCES address;