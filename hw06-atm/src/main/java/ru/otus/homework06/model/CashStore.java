package ru.otus.homework06.model;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class CashStore {
    private NavigableMap<Banknotes, Integer> store = new TreeMap<>() {
        {
            put(Banknotes.FIVE_THOUSAND, 0);
            put(Banknotes.TWO_THOUSAND, 0);
            put(Banknotes.ONE_THOUSAND, 0);
            put(Banknotes.FIVE_HUNDRED, 0);
            put(Banknotes.TWO_HUNDRED, 0);
            put(Banknotes.ONE_HUNDRED, 0);
        }
    };

    public NavigableMap<Banknotes, Integer> getStore() {
        return store;
    }

    @Override
    public String toString() {
        return store.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() > 0)
                    .map(entry -> entry.getKey() + " - " + entry.getValue() + " banknote(s)")
                    .collect(Collectors.joining(", "));
    }
}
