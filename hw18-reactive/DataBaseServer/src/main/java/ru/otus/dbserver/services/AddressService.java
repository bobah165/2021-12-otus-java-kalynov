package ru.otus.dbserver.services;


import ru.otus.dbserver.model.Address;

import java.util.Optional;

public interface AddressService {
    Optional<Address> getAddressById(long id);
    Optional<Address> saveByName(String streetName);
}
