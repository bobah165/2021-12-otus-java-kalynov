package ru.otus.homework06.service;

import ru.otus.homework06.model.Atm;
import ru.otus.homework06.model.Cash;

public class TakeMoneyService {

    public static void takeCash(Cash cashFromPerson) {
        Atm atm = AtmService.getAtmInstance();
        Cash atmCash = atm.getCash();
    }

}
