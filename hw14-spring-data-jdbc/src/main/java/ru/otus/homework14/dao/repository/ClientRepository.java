package ru.otus.homework14.dao.repository;


import org.springframework.data.repository.CrudRepository;
import ru.otus.homework14.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findAll();
    Optional<Client> findByName(String name);
}
