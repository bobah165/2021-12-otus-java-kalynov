package ru.otus.homework06;

import ru.otus.homework06.model.Cash;
import ru.otus.homework06.service.PersonService;
import ru.otus.homework06.service.TakeMoneyService;

public class AtmApplication {

    public static void main(String[] args) {
        Cash cash = PersonService.getCashFromPerson();
        TakeMoneyService.takeCash(cash);
        System.out.println("hello homework 6");
    }
}
