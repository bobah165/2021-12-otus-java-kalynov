package ru.otus.homework06;

import ru.otus.homework06.service.GiveMoneyService;
import ru.otus.homework06.service.PersonService;
import ru.otus.homework06.service.PrintService;
import ru.otus.homework06.service.TakeMoneyService;

public class AtmApplication {

    public static void main(String[] args) {
        var cash = PersonService.getCashFromPerson();
        TakeMoneyService.takeCash(cash);
        System.out.println("Current balance in ATM is " + PrintService.printTotalSumInAtm());
        int sumToGetByPerson = 3400;
        System.out.println("Sum to get by person is " + sumToGetByPerson);
        var cashBack = GiveMoneyService.giveMoney(sumToGetByPerson);
        System.out.println("Person get " + cashBack);
        System.out.println("Current balance in ATM is " + PrintService.printTotalSumInAtm());

    }
}
