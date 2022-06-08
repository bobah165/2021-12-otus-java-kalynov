package ru.otus.dbserver.services;


import ru.otus.dbserver.model.Phone;

import java.util.Optional;

public interface PhoneService {
    Optional<Phone> save(Phone phone);
}
