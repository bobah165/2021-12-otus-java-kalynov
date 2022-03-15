package ru.otus.homework06.service.impl;

import ru.otus.homework06.model.Cash;
import ru.otus.homework06.service.TakeMoneyService;
import ru.otus.homework06.util.CashStoreCreator;

import java.util.List;

public class TakeMoneyServiceImpl implements TakeMoneyService {

    public void takePersonCash(List<Cash> cashStoreFromPerson) {
        var cashStore = CashStoreCreator.createCashStoreInstance();

        cashStoreFromPerson.forEach(cash ->
            cashStore.getStore()
                        .computeIfPresent(cash.getBanknotes(), (k, v) -> v + cash.getCount()));
    }
}
