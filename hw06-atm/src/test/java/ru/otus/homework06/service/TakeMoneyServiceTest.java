package ru.otus.homework06.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework06.model.Atm;
import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;

import java.util.List;

class TakeMoneyServiceTest {

    private static Atm atm;

    @BeforeEach
    void setUp() {
        atm = AtmService.getAtmInstance();
    }

    @Test
    @DisplayName("Пользователь хочет положить 1300")
    void takePersonCash1() {
        var cashList = List.of(new Cash(Banknotes.TWO_HUNDRED, 1),
                                        new Cash(Banknotes.ONE_HUNDRED, 1),
                                        new Cash(Banknotes.ONE_THOUSAND, 1));
        TakeMoneyService.takePersonCash(cashList);

        Assertions.assertEquals(1300,atm.getTotalSum());
    }

    @Test
    @DisplayName("Пользователь хочет положить 11 000")
    void takePersonCash2() {
        var cashList = List.of(new Cash(Banknotes.TWO_HUNDRED, 5),
                                        new Cash(Banknotes.FIVE_THOUSAND, 2));
        TakeMoneyService.takePersonCash(cashList);

        Assertions.assertEquals(11000,atm.getTotalSum());
    }

    @AfterEach
    void clean() {
        atm.setTotalSum(0);
        atm.setCashStore(CashStoreService.initialize());
    }
}