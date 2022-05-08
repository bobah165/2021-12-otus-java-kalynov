package ru.otus.homework14.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework14.model.Phone;

public interface PhoneDao extends CrudRepository<Phone, Long> {
}
