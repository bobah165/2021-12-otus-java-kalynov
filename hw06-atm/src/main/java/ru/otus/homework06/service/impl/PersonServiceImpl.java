package ru.otus.homework06.service.impl;

import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;
import ru.otus.homework06.service.PersonService;

import java.util.List;


public class PersonServiceImpl implements PersonService {

    public List<Cash> getCashFromPerson() {
        return List.of( new Cash(Banknotes.TWO_HUNDRED, 3),
                        new Cash(Banknotes.ONE_HUNDRED, 5),
                        new Cash(Banknotes.ONE_THOUSAND, 1),
                        new Cash(Banknotes.FIVE_THOUSAND,1));
    }
}
