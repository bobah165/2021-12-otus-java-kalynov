package ru.otus.homework06.service;

import ru.otus.homework06.exceptions.NotEnoughBalanceException;
import ru.otus.homework06.exceptions.NotNullRemainingBalanceException;
import ru.otus.homework06.model.Atm;
import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.Cash;
import ru.otus.homework06.model.CashStore;
import ru.otus.homework06.model.CurrentSum;

public class GiveMoneyService {

    public static CashStore giveMoneyToPerson(int sum) {
        Atm atm = AtmService.getAtmInstance();
        if (atm.getTotalSum() < sum) {
            throw new NotEnoughBalanceException();
        }

        var currentSum = new CurrentSum(sum);
        var personCashStore = getCash(atm, currentSum);
        if (!currentSum.getCurrentSum().equals(0)) {
            throw new NotNullRemainingBalanceException();
        }
        CashStoreService.reCountNumberOfBanknotes(atm.getCashStore(), personCashStore);
        CalculateTotalSumInAtmService.calculateBalance(sum);
        return personCashStore;
    }

    private static CashStore getCash(Atm atm, CurrentSum sum) {
        var atmCashStore = atm.getCashStore();
        return new CashStore.Builder().setFiveThousand(new Cash(Banknotes.FIVE_THOUSAND, getCount(atmCashStore.getFiveThousand().getCount(), sum, Banknotes.FIVE_THOUSAND)))
                                      .setTwoThousand(new Cash(Banknotes.TWO_THOUSAND, getCount(atmCashStore.getTwoThousand().getCount(), sum, Banknotes.TWO_THOUSAND)))
                                      .setOneThousand(new Cash(Banknotes.ONE_THOUSAND, getCount(atmCashStore.getOneThousand().getCount(), sum, Banknotes.ONE_THOUSAND)))
                                      .setFiveHundred(new Cash(Banknotes.FIVE_HUNDRED, getCount(atmCashStore.getFiveHundred().getCount(), sum, Banknotes.FIVE_HUNDRED)))
                                      .setTwoHundred(new Cash(Banknotes.TWO_HUNDRED, getCount(atmCashStore.getTwoHundred().getCount(), sum, Banknotes.TWO_HUNDRED)))
                                      .setOneHundred(new Cash(Banknotes.ONE_HUNDRED, getCount(atmCashStore.getOneHundred().getCount(), sum, Banknotes.ONE_HUNDRED)))
                                      .build();
    }

    private static int getCount(Integer countBanknotesInAtm, CurrentSum sum, Banknotes currentBanknotes) {
        if (countBanknotesInAtm == 0) {
            return 0;
        }
        int necessaryCurrentBanknotesCount = getNecessaryCurrentBanknotesCount(countBanknotesInAtm, sum, currentBanknotes);
        setCurrentSumBalance(sum, currentBanknotes, necessaryCurrentBanknotesCount);
        return necessaryCurrentBanknotesCount;
    }

    private static void setCurrentSumBalance(CurrentSum sum, Banknotes currentBanknotes, int necessaryCurrentBanknotesCount) {
        sum.setCurrentSum(sum.getCurrentSum() - currentBanknotes.getDenomination() * necessaryCurrentBanknotesCount);
    }

    private static int getNecessaryCurrentBanknotesCount(Integer countBanknotesInAtm, CurrentSum sum, Banknotes currentBanknotes) {
        var necessaryCurrentBanknotesCount = sum.getCurrentSum() / currentBanknotes.getDenomination();
        if (countBanknotesInAtm < necessaryCurrentBanknotesCount) {
            necessaryCurrentBanknotesCount = countBanknotesInAtm;
        }
        return necessaryCurrentBanknotesCount;
    }
}
