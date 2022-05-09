package ru.otus.homework14.services;

import ru.otus.homework14.model.Address;

import java.util.Optional;

public interface AddressService {
    Optional<Address> getAddressById(long id);
    Optional<Address> saveByName(String streetName);
}
