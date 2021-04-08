package com.progressoft.jip11.parsers;

public class TransactionsParserException extends RuntimeException {

    public TransactionsParserException(String message) {
        super(message);
    }

    public TransactionsParserException(String message, Exception exception) {
        super(message, exception);
    }
}
