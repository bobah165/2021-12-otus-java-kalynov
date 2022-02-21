package ru.otus.homework06.model;

public class Cash {
    private Banknotes banknotes;
    private int count;

    public void setBanknotes(Banknotes banknotes) {
        this.banknotes = banknotes;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Banknotes getBanknotes() {
        return banknotes;
    }

    public int getCount() {
        return count;
    }

    public Cash(Banknotes banknotes, int count) {
        this.banknotes = banknotes;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Cash{" +
                "banknotes=" + banknotes +
                ", count=" + count +
                '}';
    }
}
