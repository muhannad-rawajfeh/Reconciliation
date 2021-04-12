package com.progressoft.jip11.reconciliators;

import java.io.IOException;

public class CSVTransactionsImporterException extends RuntimeException {

    public CSVTransactionsImporterException(String message) {
        super(message);
    }

    public CSVTransactionsImporterException(String message, IOException e) {
        super(message, e);
    }
}
