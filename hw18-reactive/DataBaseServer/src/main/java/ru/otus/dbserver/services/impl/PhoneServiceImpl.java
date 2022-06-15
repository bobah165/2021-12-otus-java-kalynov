package ru.otus.dbserver.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dbserver.dao.repository.PhoneRepository;
import ru.otus.dbserver.dao.sessionmanager.TransactionManager;
import ru.otus.dbserver.model.Phone;
import ru.otus.dbserver.services.PhoneService;

import java.util.Optional;

@Service
public class PhoneServiceImpl implements PhoneService {
    private final PhoneRepository repository;
    private final TransactionManager transactionManager;

    public PhoneServiceImpl(PhoneRepository repository, TransactionManager transactionManager) {
        this.repository = repository;
        this.transactionManager = transactionManager;
    }

    @Override
    public Optional<Phone> save(Phone phone) {
        return transactionManager.doInTransaction(() -> Optional.ofNullable(repository.save(phone)));
    }
}
