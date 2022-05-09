package ru.otus.homework14.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.homework14.dao.repository.AddressRepository;
import ru.otus.homework14.dao.sessionmanager.TransactionManager;
import ru.otus.homework14.model.Address;
import ru.otus.homework14.services.AddressService;

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
