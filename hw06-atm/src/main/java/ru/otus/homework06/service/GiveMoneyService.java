package ru.otus.homework06.service;

import ru.otus.homework06.model.Atm;
import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;
import ru.otus.homework06.model.CashStore;
import ru.otus.homework06.model.CurrentSum;

public class GiveMoneyService {

    public static CashStore giveMoneyToPerson(int sum) {
        Atm atm = AtmService.getAtmInstance();
        if (atm.getTotalSum() < sum) {
            return CashStoreService.initialize();
        }
        CalculateTotalSumInAtmService.calculateBalance(sum);
        var currentSum = new CurrentSum(sum);
        return getCash(currentSum);
    }

    private static CashStore getCash(CurrentSum sum) {
        return new CashStore.Builder().setFiveThousand(new Cash(Banknotes.FIVE_THOUSAND, getCount(sum, Banknotes.FIVE_THOUSAND, Banknotes.NULL)))
                                      .setTwoThousand(new Cash(Banknotes.TWO_THOUSAND, getCount(sum, Banknotes.TWO_THOUSAND, Banknotes.FIVE_THOUSAND)))
                                      .setOneThousand(new Cash(Banknotes.ONE_THOUSAND, getCount(sum, Banknotes.ONE_THOUSAND, Banknotes.TWO_THOUSAND)))
                                      .setFiveHundred(new Cash(Banknotes.FIVE_HUNDRED, getCount(sum, Banknotes.FIVE_HUNDRED, Banknotes.ONE_THOUSAND)))
                                      .setTwoHundred(new Cash(Banknotes.TWO_HUNDRED, getCount(sum, Banknotes.TWO_HUNDRED, Banknotes.FIVE_HUNDRED)))
                                      .setOneHundred(new Cash(Banknotes.ONE_HUNDRED, getCount(sum, Banknotes.ONE_HUNDRED, Banknotes.TWO_HUNDRED)))
                                      .build();
    }

    private static int getCount(CurrentSum sum, Banknotes currentBanknotes, Banknotes prevBanknotes) {
        if (prevBanknotes != Banknotes.NULL) {
            var count = (sum.getInteger() - sum.getInteger() / prevBanknotes.getDenomination()) / currentBanknotes.getDenomination();
            sum.setInteger(sum.getInteger() - currentBanknotes.getDenomination() * count);
            return count;
        }
        return sum.getInteger() / currentBanknotes.getDenomination();
    }
}
