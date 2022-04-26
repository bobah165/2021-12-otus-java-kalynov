package ru.otus.homework11.crm.service.impl;

import ru.otus.homework11.cachehw.MyCache;
import ru.otus.homework11.crm.model.Client;
import ru.otus.homework11.crm.service.CacheService;

import java.util.List;
import java.util.Optional;

public class CacheServiceImpl implements CacheService {

    private final MyCache<String, Client> myCache;

    public CacheServiceImpl(MyCache<String, Client> myCache) {
        this.myCache = myCache;
    }

    public Client saveClient(Client client) {
        myCache.put(String.valueOf(client.getId()), client);
        return client;
    }

    public Client update(Client client) {
        return myCache.update(String.valueOf(client.getId()), client);
    }

    public Optional<Client> getClient(long id) {
        return Optional.ofNullable(myCache.get(String.valueOf(id)));
    }

    public List<Client> findAll() {
        return myCache.findAll();
    }

    public boolean isDataConsistent() {
        return myCache.isResetCountZero();
    }

    public void saveAll(List<Client> clientList) {
        if(myCache.isRightSize(clientList)) {
            myCache.reset();
            clientList.forEach(client -> {
                myCache.put(String.valueOf(client.getId()),client);
            });
        }
    }
}
