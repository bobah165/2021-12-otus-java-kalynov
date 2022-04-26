package ru.otus.homework11.crm.service;

import ru.otus.homework11.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface CacheService {
    Client saveClient(Client client);

    Client update(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();

    boolean isDataConsistent();

    void saveAll(List<Client> clientList);
}
