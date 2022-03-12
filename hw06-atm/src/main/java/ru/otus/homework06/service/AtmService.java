package ru.otus.homework06.service;

import ru.otus.homework06.model.CashStore;

public interface AtmService {
    CashStore getCashStore();
    int getTotalSum();
    void reCountNumberOfBanknotes(CashStore personCashStore);
}
