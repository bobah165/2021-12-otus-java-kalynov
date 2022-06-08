package ru.otus.dbserver.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.dbserver.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
    Address save(Address address);
}
