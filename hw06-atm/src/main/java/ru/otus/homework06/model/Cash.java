package ru.otus.homework06.model;

public class Cash {
    private final Banknotes banknotes;
    private int count;

    public Cash(Banknotes banknotes, Integer count) {
        this.banknotes = banknotes;
        this.count = count;
    }

    public Banknotes getBanknotes() {
        return banknotes;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Cash{" +
                "banknotes=" + banknotes +
                ", count=" + count +
                '}';
    }
}
