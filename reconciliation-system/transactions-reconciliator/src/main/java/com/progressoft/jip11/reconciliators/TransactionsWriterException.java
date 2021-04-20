package com.progressoft.jip11.reconciliators;

import java.io.IOException;

public class TransactionsWriterException extends RuntimeException {

    public TransactionsWriterException(String message) {
        super(message);
    }

    public TransactionsWriterException(String message, IOException e) {
        super(message, e);
    }
}
