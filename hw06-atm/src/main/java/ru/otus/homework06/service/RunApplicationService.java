package ru.otus.homework06.service;

public class RunApplicationService {

    public static void run() {
        var cash = PersonService.getCashFromPerson();
        TakeMoneyService.takePersonCash(cash);
        System.out.println("Current balance in ATM is " + PrintService.printTotalSumInAtm());
        int sumThatPersonWantToGet = 3400;
        System.out.println("Sum that person want to get is " + sumThatPersonWantToGet);
        var cashBack = GiveMoneyService.giveMoneyToPerson(sumThatPersonWantToGet);
        System.out.println("Person get " + cashBack);
        System.out.println("Current balance in ATM is " + PrintService.printTotalSumInAtm());
    }
}
