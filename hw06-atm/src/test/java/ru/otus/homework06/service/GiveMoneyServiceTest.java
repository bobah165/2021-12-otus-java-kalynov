package ru.otus.homework06.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework06.exceptions.NotEnoughBalanceException;
import ru.otus.homework06.exceptions.NotNullRemainingBalanceException;
import ru.otus.homework06.model.Atm;
import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;

class GiveMoneyServiceTest {

    private static Atm atm;

    @BeforeEach
    void setUp() {
        atm = AtmService.getAtmInstance();
        atm.setTotalSum(4100);
        atm.getCashStore().setOneHundred(new Cash(Banknotes.ONE_HUNDRED,5));
        atm.getCashStore().setTwoHundred(new Cash(Banknotes.TWO_HUNDRED,3));
        atm.getCashStore().setOneThousand(new Cash(Banknotes.ONE_THOUSAND, 3));
    }

    @Test
    @DisplayName("Пользователь хочет получить 4000")
    void give4000MoneyToPerson() {
        String expected = "ONE_HUNDRED - 4 banknote(s), TWO_HUNDRED - 3 banknote(s), ONE_THOUSAND - 3 banknote(s)";

        Assertions.assertEquals(expected, GiveMoneyService.giveMoneyToPerson(4000).toString());
    }

    @Test
    @DisplayName("Пользователь хочет получить 3400")
    void give3400MoneyToPerson() {
        String expected = "TWO_HUNDRED - 2 banknote(s), ONE_THOUSAND - 3 banknote(s)";

        Assertions.assertEquals(expected, GiveMoneyService.giveMoneyToPerson(3400).toString());
    }

    @Test
    @DisplayName("Пользователь хочет получить сумму, которую ATM не может выдать")
    void give4050MoneyToPerson() {
        Assertions.assertThrows(NotNullRemainingBalanceException.class,()->GiveMoneyService.giveMoneyToPerson(4050));
    }

    @Test
    @DisplayName("Пользователь хочет получить сумму, которая больше остатка средств в ATM")
    void give10000MoneyToPerson() {
        Assertions.assertThrows(NotEnoughBalanceException.class,()->GiveMoneyService.giveMoneyToPerson(10000));
    }

    @AfterEach
    void clean() {
        atm.setTotalSum(0);
    }
}