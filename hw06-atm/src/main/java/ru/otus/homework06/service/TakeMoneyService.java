package ru.otus.homework06.service;

import ru.otus.homework06.model.Atm;
import ru.otus.homework06.model.Cash;
import ru.otus.homework06.model.CashStore;

import java.util.List;

public class TakeMoneyService {

    public static void takeCash(List<Cash> cashStoreFromPerson) {
        Atm atm = AtmService.getAtmInstance();
        CashStore atmCashStore = atm.getCashStore();

        cashStoreFromPerson.forEach(cash -> {
            addCashToAtmCashStore(cash, atmCashStore);
        });
    }

    private static void addCashToAtmCashStore(Cash cash, CashStore atmCashStore) {
        switch (cash.getBanknotes()) {
            case ONE_HUNDRED -> refreshCount(atmCashStore.getOneHundred().getCount(), cash.getCount(), atmCashStore);
            case TWO_HUNDRED -> refreshCount(atmCashStore.getTwoHundred().getCount(), cash.getCount(), atmCashStore);
            case FIVE_HUNDRED -> refreshCount(atmCashStore.getFiveHundred().getCount(), cash.getCount(), atmCashStore);
            case ONE_THOUSAND -> refreshCount(atmCashStore.getOneThousand().getCount(), cash.getCount(), atmCashStore);
            case TWO_THOUSAND -> refreshCount(atmCashStore.getTwoThousand().getCount(), cash.getCount(), atmCashStore);
            case FIVE_THOUSAND -> refreshCount(atmCashStore.getFiveThousand().getCount(), cash.getCount(), atmCashStore);
        }
    }

    private static void refreshCount(int oldCount, int newCount, CashStore atmCashStore) {
        atmCashStore.getOneHundred().setCount(oldCount + newCount);
    }
}
