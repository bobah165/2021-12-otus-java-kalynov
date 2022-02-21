package ru.otus.homework06.service;

import ru.otus.homework06.model.Atm;
import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;

public class CalculateTotalSumInAtmService {

    public static void calculate(Cash cash) {
        Atm atm = AtmService.getAtmInstance();
        var oldSumInAtm = atm.getTotalSum();
        var additionalSum = calculateAdditionalSum(cash);
        atm.setTotalSum(oldSumInAtm + additionalSum);

    }

    public static void calculateBalance(int sum) {
        Atm atm = AtmService.getAtmInstance();
        atm.setTotalSum(atm.getTotalSum()-sum);
    }

    private static long calculateAdditionalSum(Cash cash) {
        Banknotes banknotes = cash.getBanknotes();
        int count = cash.getCount();
        return (long) banknotes.getDenomination() * count;
    }
}
