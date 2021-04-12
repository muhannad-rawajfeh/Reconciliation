package com.progressoft.jip11.reconciliators;

public class TransactionsImporterFactory {

    public TransactionsImporter createImporter(Channel channel) {
        if (channel == null)
            throw new TransactionsImporterFactoryException("channel is null");
        if (channel instanceof FilePathChannel)
            return new CSVTransactionsImporter();
        throw new TransactionsImporterFactoryException("unknown communication channel");
    }
}
