package ru.otus.jdbc.service;

import ru.otus.jdbc.model.Manager;

import java.util.List;
import java.util.Optional;

public interface DBServiceManager {

    Manager saveManager(Manager client);

    Optional<Manager> getManager(long no);

    List<Manager> findAll();
}
