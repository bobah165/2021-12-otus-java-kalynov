package ru.otus.homework11;


import ru.otus.homework11.crm.service.StorageService;
import ru.otus.homework11.crm.service.impl.StorageServiceCacheImpl;
import ru.otus.homework11.crm.service.impl.StorageServiceDBImpl;


public class CacheApplication {

    public static void main(String[] args) {

        StorageService dataBase = new StorageServiceDBImpl();
        dataBase.work();

        StorageService cache = new StorageServiceCacheImpl();
        cache.work();
    }
}
