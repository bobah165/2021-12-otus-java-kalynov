package ru.otus.homework14.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.homework14.dao.repository.PhoneRepository;
import ru.otus.homework14.dao.sessionmanager.TransactionManager;
import ru.otus.homework14.model.Phone;
import ru.otus.homework14.services.PhoneService;

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
