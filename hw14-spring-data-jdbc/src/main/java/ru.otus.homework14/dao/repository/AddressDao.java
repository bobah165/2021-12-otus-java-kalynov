package ru.otus.homework14.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework14.model.Address;

public interface AddressDao extends CrudRepository<Address, Long> {
}
