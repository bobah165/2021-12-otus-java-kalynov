package ru.otus.homework18.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.homework18.dao.repository.ClientRepository;
import ru.otus.homework18.dao.sessionmanager.TransactionManager;
import ru.otus.homework18.model.Client;
import ru.otus.homework18.services.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository repository;

    public ClientServiceImpl(TransactionManager transactionManager, ClientRepository repository) {
        this.transactionManager = transactionManager;
        this.repository = repository;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = repository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInReadOnlyTransaction(() -> {
            var client = repository.findById(id);
            log.info("saved client: {}", client);
            return client;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(() -> {
            var clients = repository.findAll();
            log.info("saved client: {}", clients);
            return clients;
        });
    }

    @Override
    public Optional<Client> getByName(String name) {
        return transactionManager.doInReadOnlyTransaction(() -> {
            var client = repository.findByName(name);
            log.info("saved client: {}", client);
            return client;
        });
    }
}
