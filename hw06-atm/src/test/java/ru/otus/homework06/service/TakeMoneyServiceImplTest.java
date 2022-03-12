package ru.otus.homework06.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;
import ru.otus.homework06.model.CashStore;
import ru.otus.homework06.service.impl.AtmServiceImpl;
import ru.otus.homework06.service.impl.TakeMoneyServiceImpl;
import ru.otus.homework06.util.CashStoreCreator;

import java.util.List;

class TakeMoneyServiceImplTest {

    private TakeMoneyService takeMoneyService;
    private AtmService atmService;

    @BeforeEach
    void setUp() {
        takeMoneyService = new TakeMoneyServiceImpl();
        CashStore cashStore = CashStoreCreator.createCashStoreInstance();
        atmService = new AtmServiceImpl(cashStore);
    }

    @Test
    @DisplayName("Пользователь хочет положить 1300")
    void takePersonCash1() {
        var cashList = List.of(new Cash(Banknotes.TWO_HUNDRED, 1),
                new Cash(Banknotes.ONE_HUNDRED, 1),
                new Cash(Banknotes.ONE_THOUSAND, 1));
        takeMoneyService.takePersonCash(cashList);

        Assertions.assertEquals(1300, atmService.getTotalSum());
    }

    @Test
    @DisplayName("Пользователь хочет положить 11 000")
    void takePersonCash2() {
        var cashList = List.of(new Cash(Banknotes.TWO_HUNDRED, 5),
                new Cash(Banknotes.FIVE_THOUSAND, 2));
        takeMoneyService.takePersonCash(cashList);

        Assertions.assertEquals(11000, atmService.getTotalSum());
    }

    @AfterEach
    void clean() {
        CashStoreCreator.createCashStoreInstance()
                        .getStore()
                        .entrySet()
                        .forEach(entry -> entry.setValue(0));
    }
}