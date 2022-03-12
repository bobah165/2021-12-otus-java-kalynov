package ru.otus.homework06.util;

import ru.otus.homework06.exceptions.NotEnoughBalanceException;
import ru.otus.homework06.exceptions.NotNullRemainingBalanceException;
import ru.otus.homework06.service.impl.AtmServiceImpl;
import ru.otus.homework06.service.impl.GiveMoneyServiceImpl;
import ru.otus.homework06.service.impl.PersonServiceImpl;
import ru.otus.homework06.service.impl.TakeMoneyServiceImpl;

public class RunApplicationService {

    public static void run() {
        var cashStore = CashStoreCreator.createCashStoreInstance();

        var personService = new PersonServiceImpl();
        var cash = personService.getCashFromPerson();

        var takeMoneyService = new TakeMoneyServiceImpl();
        takeMoneyService.takePersonCash(cash);

        var atmService = new AtmServiceImpl(cashStore);
        System.out.println("Current balance in ATM is " + atmService.getCashStore());

        int sumThatPersonWantToGet = 6000;
        System.out.println("Sum that person want to get is " + sumThatPersonWantToGet);

        try {
            var giveMoneyService = new GiveMoneyServiceImpl();
            var cashBack = giveMoneyService.giveMoneyToPerson(sumThatPersonWantToGet);
            System.out.println("Person get " + cashBack);
        } catch (NotEnoughBalanceException | NotNullRemainingBalanceException e) {
            System.out.println("It is not possible return this cash. Fill another value");
        }

        System.out.println("Current balance in ATM is " + atmService.getTotalSum());
        System.out.println("Remaining banknotes in ATM " + atmService.getCashStore().getStore());
    }
}
