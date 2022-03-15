package ru.otus.homework06.service.impl;

import ru.otus.homework06.exceptions.NotEnoughBalanceException;
import ru.otus.homework06.exceptions.NotNullRemainingBalanceException;
import ru.otus.homework06.model.Banknotes;
import ru.otus.homework06.model.CashStore;
import ru.otus.homework06.model.CurrentSum;
import ru.otus.homework06.service.GiveMoneyService;
import ru.otus.homework06.util.CashStoreCreator;

public class GiveMoneyServiceImpl implements GiveMoneyService {

    public CashStore giveMoneyToPerson(int sum) {
        var cashStore = CashStoreCreator.createCashStoreInstance();
        var atmService = new AtmServiceImpl(cashStore);

        if (atmService.getTotalSum() < sum) {
            throw new NotEnoughBalanceException();
        }

        var currentSum = new CurrentSum(sum);
        var personCashStore = getCash(cashStore, currentSum);

        if (!currentSum.getCurrentSum().equals(0)) {
            throw new NotNullRemainingBalanceException();
        }
        atmService.reCountNumberOfBanknotes(personCashStore);
        return personCashStore;
    }

    private static CashStore getCash(CashStore cashStore, CurrentSum sum) {
        var store = cashStore.getStore();
        var personCashStore = new CashStore();

        personCashStore.getStore()
                       .descendingMap()
                       .entrySet()
                       .forEach(entry -> entry.setValue(getCount(store.get(entry.getKey()), sum, entry.getKey())));

        return personCashStore;
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
