package ru.otus.homework18.services;


import ru.otus.homework18.model.Address;

import java.util.Optional;

public interface AddressService {
    Optional<Address> getAddressById(long id);
    Optional<Address> saveByName(String streetName);
}
