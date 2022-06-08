package ru.otus.dbserver.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.dbserver.dao.repository.AddressRepository;
import ru.otus.dbserver.dao.sessionmanager.TransactionManager;
import ru.otus.dbserver.model.Address;
import ru.otus.dbserver.services.AddressService;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final TransactionManager transactionManager;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(TransactionManager transactionManager, AddressRepository addressRepository) {
        this.transactionManager = transactionManager;
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<Address> getAddressById(long id) {
        return transactionManager.doInReadOnlyTransaction(() -> addressRepository.findById(id));
    }

    @Override
    public Optional<Address> saveByName(String streetName) {
        return transactionManager.doInTransaction(() -> {
            var address = new Address(streetName);
            return Optional.ofNullable(addressRepository.save(address));
        });
    }
}
