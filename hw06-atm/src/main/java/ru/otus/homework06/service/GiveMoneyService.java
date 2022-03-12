package ru.otus.homework06.service;

import ru.otus.homework06.model.CashStore;

public interface GiveMoneyService {
    CashStore giveMoneyToPerson(int sum);
}
