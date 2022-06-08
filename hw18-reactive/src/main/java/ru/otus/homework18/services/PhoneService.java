package ru.otus.homework18.services;


import ru.otus.homework18.model.Phone;

import java.util.Optional;

public interface PhoneService {
    Optional<Phone> save(Phone phone);
}
