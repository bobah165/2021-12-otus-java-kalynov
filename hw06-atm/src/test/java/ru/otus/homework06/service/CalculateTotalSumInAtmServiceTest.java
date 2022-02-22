package ru.otus.homework06.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework06.model.Atm;
import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;

class CalculateTotalSumInAtmServiceTest {

    private static Atm atm;

    @BeforeEach
    void setUp() {
        atm = AtmService.getAtmInstance();
    }

    @Test
    @DisplayName("Вычисляем количество средств в банкомате")
    void calculate() {
        Cash cash = new Cash(Banknotes.ONE_HUNDRED, 2);
        CalculateTotalSumInAtmService.calculate(cash);

        Assertions.assertEquals(atm.getTotalSum(),200);
    }

    @Test
    @DisplayName("Получаем остаток средств в банкомате")
    void calculateBalance() {
        atm.setTotalSum(1000);
        CalculateTotalSumInAtmService.calculateBalance(200);

        Assertions.assertEquals(atm.getTotalSum(),800);
    }

    @AfterEach
    void clean() {
        atm.setTotalSum(0);
    }
}