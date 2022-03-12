package ru.otus.homework06.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework06.exceptions.NotEnoughBalanceException;
import ru.otus.homework06.exceptions.NotNullRemainingBalanceException;
import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.CashStore;
import ru.otus.homework06.service.impl.GiveMoneyServiceImpl;
import ru.otus.homework06.util.CashStoreCreator;

class GiveMoneyServiceImplTest {

    private GiveMoneyService giveMoneyService;

    @BeforeEach
    void setUp() {
        giveMoneyService = new GiveMoneyServiceImpl();
        CashStore cashStoreService = CashStoreCreator.createCashStoreInstance();
        cashStoreService.getStore().computeIfPresent(Banknotes.ONE_HUNDRED, (k, v) -> v + 5);
        cashStoreService.getStore().computeIfPresent(Banknotes.TWO_HUNDRED, (k, v) -> v + 3);
        cashStoreService.getStore().computeIfPresent(Banknotes.ONE_THOUSAND, (k, v) -> v + 3);
    }

    @Test
    @DisplayName("Пользователь хочет получить 4000")
    void give4000MoneyToPerson() {
        String expected = "ONE_HUNDRED - 4 banknote(s), TWO_HUNDRED - 3 banknote(s), ONE_THOUSAND - 3 banknote(s)";

        Assertions.assertEquals(expected, giveMoneyService.giveMoneyToPerson(4000).toString());
    }

    @Test
    @DisplayName("Пользователь хочет получить 3400")
    void give3400MoneyToPerson() {
        String expected = "TWO_HUNDRED - 2 banknote(s), ONE_THOUSAND - 3 banknote(s)";

        Assertions.assertEquals(expected, giveMoneyService.giveMoneyToPerson(3400).toString());
    }

    @Test
    @DisplayName("Пользователь хочет получить сумму, которую ATM не может выдать")
    void give4050MoneyToPerson() {
        Assertions.assertThrows(NotNullRemainingBalanceException.class, () -> giveMoneyService.giveMoneyToPerson(4050));
    }

    @Test
    @DisplayName("Пользователь хочет получить сумму, которая больше остатка средств в ATM")
    void give10000MoneyToPerson() {
        Assertions.assertThrows(NotEnoughBalanceException.class, () -> giveMoneyService.giveMoneyToPerson(10000));
    }

    @AfterEach
    void clean() {
        CashStoreCreator.createCashStoreInstance()
                        .getStore()
                        .entrySet()
                        .forEach(entry -> entry.setValue(0));
    }
}