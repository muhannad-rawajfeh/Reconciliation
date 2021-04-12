package com.progressoft.jip11.reconciliators;

import java.io.IOException;

public class TransactionsImporterException extends RuntimeException {

    public TransactionsImporterException(String message) {
        super(message);
    }

    public TransactionsImporterException(String message, IOException e) {
        super(message, e);
    }
}
