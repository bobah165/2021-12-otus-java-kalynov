package ru.otus.homework11.crm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework11.core.repository.DataTemplate;
import ru.otus.homework11.core.sessionmanager.TransactionManager;
import ru.otus.homework11.crm.model.Client;
import ru.otus.homework11.crm.service.CacheService;
import ru.otus.homework11.crm.service.DBServiceClient;

import java.util.List;
import java.util.Optional;

public class DbServiceClientDataBaseImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientDataBaseImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final CacheService cacheService;
    private final TransactionManager transactionManager;

    public DbServiceClientDataBaseImpl(DataTemplate<Client> clientDataTemplate, CacheService cacheService, TransactionManager transactionManager) {
        this.clientDataTemplate = clientDataTemplate;
        this.cacheService = cacheService;
        this.transactionManager = transactionManager;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                cacheService.saveClient(clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);

            cacheService.update(clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        if (cacheService.getClient(id).isPresent()) {
            return cacheService.getClient(id);
        }
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        if (cacheService.isDataConsistent()) {
           return cacheService.findAll();
        }
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            cacheService.saveAll(clientList);
            return clientList;
       });
    }
}
