package ru.otus.homework06.service;

import ru.otus.homework06.model.Cash;

import java.util.List;

public interface TakeMoneyService {
    void takePersonCash(List<Cash> cashStoreFromPerson);
}
