package ru.otus.homework18.services;


import ru.otus.homework18.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client saveClient(Client client);
    Optional<Client> getClient(long id);
    List<Client> findAll();
    Optional<Client> getByName(String name);
}
