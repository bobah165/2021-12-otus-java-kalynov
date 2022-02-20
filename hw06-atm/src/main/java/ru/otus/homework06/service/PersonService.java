package ru.otus.homework06.service;

import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;
import ru.otus.homework06.model.Cell;

public class PersonService {

    public static Cash getCashFromPerson() {
        return new Cash.Builder()
                        .setFiveHundred(new Cell(Banknotes.FIVE_HUNDRED, 10))
                        .setTwoHundred(new Cell(Banknotes.TWO_HUNDRED,3))
                        .build();
    }
}
