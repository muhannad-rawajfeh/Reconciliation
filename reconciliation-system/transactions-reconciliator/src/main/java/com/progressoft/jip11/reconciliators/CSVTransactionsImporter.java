package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;

import java.util.List;

public class CSVTransactionsImporter implements TransactionsImporter {

    @Override
    public void importMatchingTransactions(Channel channel, List<Transaction> transactions) {
        validateChannelAndList(channel, transactions);
    }

    @Override
    public void importOtherTransactions(Channel channel, List<SourcedTransaction> transactions) {
    }

    private void validateChannelAndList(Channel channel, List<Transaction> transactions) {
        if (channel == null)
            throw new CSVTransactionsImporterException("channel is null");
        if (!(channel instanceof FilePathChannel))
            throw new CSVTransactionsImporterException("invalid communication channel");
        if (transactions == null)
            throw new CSVTransactionsImporterException("list is null");
    }
}
