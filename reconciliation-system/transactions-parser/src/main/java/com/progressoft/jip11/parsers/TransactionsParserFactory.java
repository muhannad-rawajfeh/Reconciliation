package com.progressoft.jip11.parsers;

import java.util.Arrays;
import java.util.List;

public class TransactionsParserFactory {

    public static final List<String> supportedTypes = Arrays.asList("CSV", "JSON");

    public static TransactionsParser createParser(String format) {
        if (format == null)
            throw new TransactionsParserFactoryException("format is null");
        if (format.equalsIgnoreCase("csv"))
            return new CSVTransactionsParser();
        if (format.equalsIgnoreCase("json"))
            return new JSONTransactionsParser();
        throw new TransactionsParserFactoryException("invalid format or not yet supported");
    }
}
