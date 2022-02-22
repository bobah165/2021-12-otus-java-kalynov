package ru.otus.homework06.service;

import ru.otus.homework06.model.Atm;
import ru.otus.homework06.model.Cash;
import ru.otus.homework06.model.CashStore;

import java.util.List;

public class TakeMoneyService {

    public static void takePersonCash(List<Cash> cashStoreFromPerson) {
        Atm atm = AtmService.getAtmInstance();
        CashStore atmCashStore = atm.getCashStore();

        cashStoreFromPerson.forEach(cash -> {
            addCashToAtmCashStore(cash, atmCashStore);
            CalculateTotalSumInAtmService.calculate(cash);
        });
    }

    private static void addCashToAtmCashStore(Cash cash, CashStore atmCashStore) {
        switch (cash.getBanknotes()) {
            case ONE_HUNDRED -> refreshCount(atmCashStore.getOneHundred().getCount(), cash.getCount(), atmCashStore.getOneHundred());
            case TWO_HUNDRED -> refreshCount(atmCashStore.getTwoHundred().getCount(), cash.getCount(), atmCashStore.getTwoHundred());
            case FIVE_HUNDRED -> refreshCount(atmCashStore.getFiveHundred().getCount(), cash.getCount(), atmCashStore.getFiveHundred());
            case ONE_THOUSAND -> refreshCount(atmCashStore.getOneThousand().getCount(), cash.getCount(), atmCashStore.getOneThousand());
            case TWO_THOUSAND -> refreshCount(atmCashStore.getTwoThousand().getCount(), cash.getCount(), atmCashStore.getTwoThousand());
            case FIVE_THOUSAND -> refreshCount(atmCashStore.getFiveThousand().getCount(), cash.getCount(), atmCashStore.getFiveThousand());
        }
    }

    private static void refreshCount(int oldCount, int newCount, Cash cash) {
        cash.setCount(oldCount + newCount);
    }
}
