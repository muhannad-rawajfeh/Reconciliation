package com.progressoft.jip11.reconciliators;

public class TransactionsWriterFactory {

    public static TransactionsWriter createWriter(String format) {
        if (format == null)
            throw new IllegalArgumentException("format is null");
        if (format.equalsIgnoreCase("csv"))
            return new CSVTransactionsWriter();
        if (format.equalsIgnoreCase("json"))
            return new JSONTransactionsWriter();
        throw new IllegalArgumentException("invalid format or not yet supported");
    }
}
