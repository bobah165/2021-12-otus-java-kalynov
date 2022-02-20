package ru.otus.homework06.service;

import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;

import java.util.List;


public class PersonService {

    public static List<Cash> getCashFromPerson() {
        return List.of(new Cash(Banknotes.TWO_HUNDRED, 3),
                        new Cash(Banknotes.ONE_HUNDRED,5));
    }
}
