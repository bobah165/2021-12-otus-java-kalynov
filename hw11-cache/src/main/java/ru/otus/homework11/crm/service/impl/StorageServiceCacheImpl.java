package ru.otus.homework11.crm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework11.cachehw.MyCache;
import ru.otus.homework11.crm.model.Client;
import ru.otus.homework11.crm.service.ClientService;
import ru.otus.homework11.crm.service.StorageService;

import java.util.ArrayList;

public class StorageServiceCacheImpl implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageServiceCacheImpl.class);

    public void work() {
        MyCache<String, Client> myCache = new MyCache<>();
        var dbServiceClient = new DbServiceCacheImpl(myCache);

        checkWorkingTime(dbServiceClient);

        checkResetOfCacheMemory(myCache, dbServiceClient);
    }

    private void checkWorkingTime(DbServiceCacheImpl dbServiceClient) {
        var startCacheTime = System.currentTimeMillis();

        ClientService clientService = new ClientService();
        clientService.workWithClient(dbServiceClient);

        log.info("Cache time " + (System.currentTimeMillis() - startCacheTime));
    }

    private void checkResetOfCacheMemory(MyCache<String, Client> myCache, DbServiceCacheImpl dbServiceClient) {
        for (int i = 0; i < 20000; i++) {
            dbServiceClient.saveClient(new Client("dbServiceFirst"));
        }
        var clients = new ArrayList<>(myCache.findAll());

        clients.sort((c1, c2) -> {
            var dif = c1.getId() - c2.getId();
            return Integer.parseInt(String.valueOf(dif));
        });

        clients.forEach(client -> System.out.println(client.getId()));
    }
}
