package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;

import java.util.List;

public interface TransactionsImporter {

    void importMatchingTransactions(Channel channel, List<Transaction> transactions);

    void importOtherTransactions(Channel channel, List<SourcedTransaction> transactions);
}
