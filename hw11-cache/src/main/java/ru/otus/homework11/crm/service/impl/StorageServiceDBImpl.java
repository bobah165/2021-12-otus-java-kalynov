package ru.otus.homework11.crm.service.impl;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework11.core.repository.DataTemplateHibernate;
import ru.otus.homework11.core.repository.EntityGraphUtil;
import ru.otus.homework11.core.repository.HibernateUtils;
import ru.otus.homework11.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.homework11.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.homework11.crm.model.Address;
import ru.otus.homework11.crm.model.Client;
import ru.otus.homework11.crm.model.Phone;
import ru.otus.homework11.crm.service.ClientService;
import ru.otus.homework11.crm.service.StorageService;

public class StorageServiceDBImpl implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageServiceDBImpl.class);
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public void work() {
        var startDBTime = System.currentTimeMillis();

        var transactionManager = getTransaction();
        var clientTemplate = getClientTemplate();
        var dbServiceClient = new DbServiceClientDataBaseImpl(transactionManager, clientTemplate);

        ClientService clientService = new ClientService();
        clientService.workWithClient(dbServiceClient);

        log.info("End DB time " + (System.currentTimeMillis() - startDBTime));
    }

    private static DataTemplateHibernate<Client> getClientTemplate() {
        var entityGraphUtil = new EntityGraphUtil<>(Client.class);
        return new DataTemplateHibernate<>(Client.class, entityGraphUtil);
    }

    private static TransactionManagerHibernate getTransaction() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        return new TransactionManagerHibernate(sessionFactory);
    }
}
