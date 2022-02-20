package ru.otus.homework06.model;

public class Atm {
    private long totalSum;
    private CashStore cashStore;

    public long getTotalSum() {
        return totalSum;
    }

    public CashStore getCashStore() {
        return cashStore;
    }

    public void setTotalSum(long totalSum) {
        this.totalSum = totalSum;
    }

    public void setCashStore(CashStore cashStore) {
        this.cashStore = cashStore;
    }
}
