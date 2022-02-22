package ru.otus.homework06.service;

import ru.otus.homework06.exceptions.NotEnoughBalanceException;
import ru.otus.homework06.exceptions.NotNullRemainingBalanceException;

public class RunApplicationService {

    public static void run() {
        var cash = PersonService.getCashFromPerson();
        TakeMoneyService.takePersonCash(cash);
        System.out.println("Current balance in ATM is " + PrintService.printTotalSumInAtm());
        int sumThatPersonWantToGet = 6000;
        System.out.println("Sum that person want to get is " + sumThatPersonWantToGet);
        try {
            var cashBack = GiveMoneyService.giveMoneyToPerson(sumThatPersonWantToGet);
            System.out.println("Person get " + cashBack);
        } catch (NotEnoughBalanceException | NotNullRemainingBalanceException e) {
            System.out.println("It is not possible return this cash. Fill another value");
        }
        System.out.println("Current balance in ATM is " + PrintService.printTotalSumInAtm());
        PrintService.printBalanceByBanknotes();
    }
}
