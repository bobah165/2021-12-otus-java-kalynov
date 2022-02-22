package ru.otus.homework06.service;

import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;
import ru.otus.homework06.model.CashStore;

public class CashStoreService {

    public static CashStore initialize() {
        return new CashStore.Builder().setOneHundred(new Cash(Banknotes.ONE_HUNDRED, 0))
                                      .setTwoHundred(new Cash(Banknotes.TWO_HUNDRED, 0))
                                      .setFiveHundred(new Cash(Banknotes.FIVE_HUNDRED, 0))
                                      .setOneThousand(new Cash(Banknotes.ONE_THOUSAND, 0))
                                      .setTwoThousand(new Cash(Banknotes.TWO_THOUSAND, 0))
                                      .setFiveThousand(new Cash(Banknotes.FIVE_THOUSAND, 0))
                                      .build();
    }

    public static void reCountNumberOfBanknotes(CashStore atmCashStore, CashStore personCashStore) {
        atmCashStore.getOneHundred().setCount(atmCashStore.getOneHundred().getCount() - personCashStore.getOneHundred().getCount());
        atmCashStore.getTwoHundred().setCount(atmCashStore.getTwoHundred().getCount() - personCashStore.getTwoHundred().getCount());
        atmCashStore.getFiveHundred().setCount(atmCashStore.getFiveHundred().getCount() - personCashStore.getFiveHundred().getCount());
        atmCashStore.getOneThousand().setCount(atmCashStore.getOneThousand().getCount() - personCashStore.getOneThousand().getCount());
        atmCashStore.getTwoThousand().setCount(atmCashStore.getTwoThousand().getCount() - personCashStore.getTwoThousand().getCount());
        atmCashStore.getFiveThousand().setCount(atmCashStore.getFiveThousand().getCount() - personCashStore.getFiveThousand().getCount());
    }
}
