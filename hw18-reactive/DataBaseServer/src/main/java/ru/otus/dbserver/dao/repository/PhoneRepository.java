package ru.otus.dbserver.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.dbserver.model.Phone;


public interface PhoneRepository extends CrudRepository<Phone, Long> {
    Phone save(Phone phone);
}
