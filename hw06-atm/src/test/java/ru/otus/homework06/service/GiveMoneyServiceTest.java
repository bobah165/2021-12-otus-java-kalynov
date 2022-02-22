package ru.otus.homework06.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework06.model.Atm;

class GiveMoneyServiceTest {

    private static Atm atm;

    @BeforeEach
    void setUp() {
        atm = AtmService.getAtmInstance();
    }

    @Test
    @DisplayName("Пользователь хочет получить 4000")
    void give4000MoneyToPerson() {
        atm.setTotalSum(5000);

        Assertions.assertEquals("TWO_THOUSAND - 2 banknote(s)", GiveMoneyService.giveMoneyToPerson(4000).toString());
    }

    @Test
    @DisplayName("Пользователь хочет получить 3400")
    void give3400MoneyToPerson() {
        atm.setTotalSum(5000);
        String expected = "TWO_HUNDRED - 2 banknote(s), ONE_THOUSAND - 1 banknote(s), TWO_THOUSAND - 1 banknote(s)";

        Assertions.assertEquals(expected, GiveMoneyService.giveMoneyToPerson(3400).toString());
    }

    @AfterEach
    void clean() {
        atm.setTotalSum(0);
    }
}