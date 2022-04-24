package ru.otus.homework11.crm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework11.core.repository.DataTemplate;
import ru.otus.homework11.core.sessionmanager.TransactionManager;
import ru.otus.homework11.crm.model.Client;
import ru.otus.homework11.crm.service.DBServiceClient;

import java.util.List;
import java.util.Optional;

public class DbServiceClientDataBaseImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientDataBaseImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;

    public DbServiceClientDataBaseImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
       });
    }

    @Override
    public void removeClientById(Long id) {
    }
}
