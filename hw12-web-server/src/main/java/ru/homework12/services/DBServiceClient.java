package ru.homework12.services;

import ru.homework12.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);
    Optional<Client> getClient(long id);
    List<Client> findAll();
    Optional<Client> getByName(String name);
}
