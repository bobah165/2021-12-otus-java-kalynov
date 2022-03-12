package ru.otus.homework06.util;

import ru.otus.homework06.model.CashStore;

public class CashStoreCreator {
    private static CashStore cashStore;

    private CashStoreCreator(CashStore cashStore) {
        this.cashStore = cashStore;
    }

    public static CashStore createCashStoreInstance() {
        if (cashStore == null) {
            cashStore = new CashStore();
        }
        return cashStore;
    }
}
