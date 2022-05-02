package ru.homework12.config;

import org.hibernate.cfg.Configuration;
import ru.homework12.dao.dbmigrations.MigrationsExecutorFlyway;
import ru.homework12.dao.repository.DataTemplateHibernate;
import ru.homework12.dao.repository.EntityGraphUtil;
import ru.homework12.dao.repository.HibernateUtils;
import ru.homework12.dao.sessionmanager.TransactionManagerHibernate;
import ru.homework12.model.Address;
import ru.homework12.model.Client;
import ru.homework12.model.Phone;
import ru.homework12.services.impl.DbServiceClientImpl;

public class DbConfig {

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public DbServiceClientImpl getServiceClient() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        EntityGraphUtil<Client> entityGraphUtil = new EntityGraphUtil<>(Client.class);
        var clientTemplate = new DataTemplateHibernate<>(Client.class, entityGraphUtil);

        return new DbServiceClientImpl(transactionManager, clientTemplate);
    }
}
