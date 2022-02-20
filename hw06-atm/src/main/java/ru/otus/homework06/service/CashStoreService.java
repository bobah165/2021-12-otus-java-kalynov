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
}
