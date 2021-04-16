package com.progressoft.jip11.reconciliators;

import java.io.IOException;

public class TransactionsExporterException extends RuntimeException {

    public TransactionsExporterException(String message) {
        super(message);
    }

    public TransactionsExporterException(String message, IOException e) {
        super(message, e);
    }
}
