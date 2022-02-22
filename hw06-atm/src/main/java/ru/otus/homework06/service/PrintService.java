package ru.otus.homework06.service;

import ru.otus.homework06.model.Atm;

public class PrintService {

    public static long printTotalSumInAtm() {
        Atm atm = AtmService.getAtmInstance();
        return atm.getTotalSum();
    }

    public static void printBalanceByBanknotes() {
        Atm atm = AtmService.getAtmInstance();
        System.out.println("Remaining banknotes in ATM " + atm.getCashStore());
    }
}
