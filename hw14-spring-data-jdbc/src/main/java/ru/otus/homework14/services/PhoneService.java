package ru.otus.homework14.services;

import ru.otus.homework14.model.Phone;

import java.util.Optional;

public interface PhoneService {
    Optional<Phone> save(Phone phone);
}
