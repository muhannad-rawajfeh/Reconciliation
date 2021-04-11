package com.progressoft.jip11.parsers;

public class TransactionsParserFactory {

    public TransactionsParser createParser(String format) {
        if (format == null)
            throw new TransactionsParserFactoryException("format is null");
        if (format.equalsIgnoreCase("csv"))
            return new CSVTransactionsParser();
        if (format.equalsIgnoreCase("json"))
            return new JSONTransactionsParser();
        throw new TransactionsParserFactoryException("unknown format");
    }
}
