package ru.otus.homework11.crm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework11.cachehw.HWCacheDemo;
import ru.otus.homework11.cachehw.MyCache;
import ru.otus.homework11.crm.model.Client;
import ru.otus.homework11.crm.service.DBServiceClient;

import java.util.List;
import java.util.Optional;

public class DbServiceCacheImpl implements DBServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    private final MyCache<String, Client> myCache;
    private static long count = 1;

    public DbServiceCacheImpl(MyCache<String, Client> myCache) {
        this.myCache = myCache;
    }

    @Override
    public Client saveClient(Client client) {

        checkIdClient(client);

        if (isExistClientInCache(client)){
            return myCache.update(String.valueOf(client.getId()), client);
        }

        myCache.put(String.valueOf(client.getId()), client);
        return client;
    }

    @Override
    public Optional<Client> getClient(long id) {
        return Optional.ofNullable(myCache.get(String.valueOf(id)));
    }

    @Override
    public List<Client> findAll() {
       return myCache.findAll();
    }

    @Override
    public void removeClientById(Long id) {
        myCache.remove(String.valueOf(id));
    }

    private boolean isExistClientInCache(Client client) {
        return myCache.findAll().stream().anyMatch(client1 -> client1.getId().equals(client.getId()));
    }

    private void checkIdClient(Client client) {
        if (client.getId() == null) {
            String id = generateKey();
            client.setId(Long.parseLong(id));
        }
    }

    private String generateKey() {
        count++;
        return String.valueOf(count);
    }
}
