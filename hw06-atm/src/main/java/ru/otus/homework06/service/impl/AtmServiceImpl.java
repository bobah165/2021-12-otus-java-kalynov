package ru.otus.homework06.service.impl;

import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.CashStore;
import ru.otus.homework06.service.AtmService;

import java.util.Map;

public class AtmServiceImpl implements AtmService {
    private final CashStore cashStore;

    public AtmServiceImpl(CashStore cashStore) {
        this.cashStore = cashStore;
    }

    public CashStore getCashStore() {
        return cashStore;
    }

    public int getTotalSum() {
        return cashStore.getStore()
                        .entrySet()
                        .stream()
                        .map(AtmServiceImpl::getSumOneTypeOfBanknotes)
                        .reduce(0, Integer::sum);
    }

    public void reCountNumberOfBanknotes(CashStore personCashStore) {
        cashStore.getStore()
                 .entrySet()
                 .forEach(entry -> entry.setValue(entry.getValue() - personCashStore.getStore().get(entry.getKey())));
    }

    private static int getSumOneTypeOfBanknotes(Map.Entry<Banknotes, Integer> entry) {
        return entry.getKey().getDenomination() * entry.getValue();
    }
}
