package ru.otus.homework18.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework18.model.Phone;


public interface PhoneRepository extends CrudRepository<Phone, Long> {
    Phone save(Phone phone);
}
