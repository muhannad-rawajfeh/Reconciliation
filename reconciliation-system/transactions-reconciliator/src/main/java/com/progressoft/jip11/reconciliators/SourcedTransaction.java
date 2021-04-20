package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;

import java.util.Objects;

public class SourcedTransaction {

    private final String source;
    private final Transaction transaction;

    public SourcedTransaction(String source, Transaction transaction) {
        this.source = source;
        this.transaction = transaction;
    }

    public String getSource() {
        return source;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return source + "," + transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourcedTransaction that = (SourcedTransaction) o;
        return Objects.equals(source, that.source) && Objects.equals(transaction, that.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, transaction);
    }
}
