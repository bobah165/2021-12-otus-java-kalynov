package ru.otus.homework06.model;

public class Cash {
    private final Cell oneHundred;
    private final Cell twoHundred;
    private final Cell fiveHundred;
    private final Cell oneThousand;
    private final Cell twoThousand;
    private final Cell fiveThousand;

    public Cell getOneHundred() {
        return oneHundred;
    }

    public Cell getTwoHundred() {
        return twoHundred;
    }

    public Cell getFiveHundred() {
        return fiveHundred;
    }

    public Cell getOneThousand() {
        return oneThousand;
    }

    public Cell getTwoThousand() {
        return twoThousand;
    }

    public Cell getFiveThousand() {
        return fiveThousand;
    }

    private Cash(Builder builder) {
        this.oneHundred = builder.oneHundred;
        this.twoHundred = builder.twoHundred;
        this.fiveHundred = builder.fiveHundred;
        this.oneThousand = builder.oneThousand;
        this.twoThousand = builder.twoThousand;
        this.fiveThousand = builder.fiveThousand;
    }

    public static class Builder{
        private Cell oneHundred;
        private Cell twoHundred;
        private Cell fiveHundred;
        private Cell oneThousand;
        private Cell twoThousand;
        private Cell fiveThousand;

        public Builder setOneHundred(Cell oneHundred) {
            this.oneHundred = oneHundred;
            return this;
        }

        public Builder setTwoHundred(Cell twoHundred) {
            this.twoHundred = twoHundred;
            return this;
        }

        public Builder setFiveHundred(Cell fiveHundred) {
            this.fiveHundred = fiveHundred;
            return this;
        }

        public Builder setOneThousand(Cell oneThousand) {
            this.oneThousand = oneThousand;
            return this;
        }

        public Builder setTwoThousand(Cell twoThousand) {
            this.twoThousand = twoThousand;
            return this;
        }

        public Builder setFiveThousand(Cell fiveThousand) {
            this.fiveThousand = fiveThousand;
            return this;
        }

        public Cash build() {
            return new Cash(this);
        }
    }
}
