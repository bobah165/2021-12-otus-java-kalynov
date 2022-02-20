package ru.otus.homework06.model;

public class Atm {
    private long totalSum;
    private Cash cash;

    public long getTotalSum() {
        return totalSum;
    }

    public Cash getCash() {
        return cash;
    }

    public void setTotalSum(long totalSum) {
        this.totalSum = totalSum;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }
}
