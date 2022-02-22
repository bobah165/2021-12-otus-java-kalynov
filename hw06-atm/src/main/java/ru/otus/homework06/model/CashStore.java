package ru.otus.homework06.model;

import java.util.List;
import java.util.stream.Collectors;

public class CashStore {
    private Cash oneHundred;
    private Cash twoHundred;
    private Cash fiveHundred;
    private Cash oneThousand;
    private Cash twoThousand;
    private Cash fiveThousand;

    @Override
    public String toString() {
        var cashList = List.of(this.getOneHundred(), this.getTwoHundred(), this.getFiveHundred(),
                this.getOneThousand(), this.getTwoThousand(), this.getFiveThousand());
        return cashList.stream()
                       .filter(cash -> cash.getCount() > 0)
                       .map(cash -> cash.getBanknotes().toString() + " - " + cash.getCount() + " banknote(s)")
                       .collect(Collectors.joining(", "));
    }

    public void setOneHundred(Cash oneHundred) {
        this.oneHundred = oneHundred;
    }

    public void setTwoHundred(Cash twoHundred) {
        this.twoHundred = twoHundred;
    }

    public void setFiveHundred(Cash fiveHundred) {
        this.fiveHundred = fiveHundred;
    }

    public void setOneThousand(Cash oneThousand) {
        this.oneThousand = oneThousand;
    }

    public void setTwoThousand(Cash twoThousand) {
        this.twoThousand = twoThousand;
    }

    public void setFiveThousand(Cash fiveThousand) {
        this.fiveThousand = fiveThousand;
    }

    public Cash getOneHundred() {
        return oneHundred;
    }

    public Cash getTwoHundred() {
        return twoHundred;
    }

    public Cash getFiveHundred() {
        return fiveHundred;
    }

    public Cash getOneThousand() {
        return oneThousand;
    }

    public Cash getTwoThousand() {
        return twoThousand;
    }

    public Cash getFiveThousand() {
        return fiveThousand;
    }

    private CashStore(Builder builder) {
        this.oneHundred = builder.oneHundred;
        this.twoHundred = builder.twoHundred;
        this.fiveHundred = builder.fiveHundred;
        this.oneThousand = builder.oneThousand;
        this.twoThousand = builder.twoThousand;
        this.fiveThousand = builder.fiveThousand;
    }

    public static class Builder {
        private Cash oneHundred;
        private Cash twoHundred;
        private Cash fiveHundred;
        private Cash oneThousand;
        private Cash twoThousand;
        private Cash fiveThousand;

        public Builder setOneHundred(Cash oneHundred) {
            this.oneHundred = oneHundred;
            return this;
        }

        public Builder setTwoHundred(Cash twoHundred) {
            this.twoHundred = twoHundred;
            return this;
        }

        public Builder setFiveHundred(Cash fiveHundred) {
            this.fiveHundred = fiveHundred;
            return this;
        }

        public Builder setOneThousand(Cash oneThousand) {
            this.oneThousand = oneThousand;
            return this;
        }

        public Builder setTwoThousand(Cash twoThousand) {
            this.twoThousand = twoThousand;
            return this;
        }

        public Builder setFiveThousand(Cash fiveThousand) {
            this.fiveThousand = fiveThousand;
            return this;
        }

        public CashStore build() {
            return new CashStore(this);
        }
    }
}
