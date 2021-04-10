package com.progressoft.jip11.parsers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

public class Transaction {

    private final String id;
    private final BigDecimal amount;
    private final Currency currency;
    private final LocalDate date;

    public Transaction(Builder builder) {
        id = builder.id;
        amount = builder.amount;
        currency = builder.currency;
        date = builder.date;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return id + "," + amount + "," + currency + "," + date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id)
                && Objects.equals(amount, that.amount)
                && Objects.equals(currency, that.currency)
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currency, date);
    }

    public static class Builder {

        private String id;
        private BigDecimal amount;
        private Currency currency;
        private LocalDate date;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder setCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
