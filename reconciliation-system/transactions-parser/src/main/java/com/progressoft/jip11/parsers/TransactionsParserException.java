package com.progressoft.jip11.parsers;

import java.io.IOException;

public class TransactionsParserException extends RuntimeException {

    public TransactionsParserException(String message) {
        super(message);
    }

    public TransactionsParserException(String message, IOException e) {
        super(message, e);
    }
}
