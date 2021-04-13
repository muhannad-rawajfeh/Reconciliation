package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.parsers.ValidPath;

import java.util.List;

public interface TransactionsFileImporter {

    void importMatchingTransactions(ValidPath validPath, List<Transaction> transactions);

    void importOtherTransactions(ValidPath validPath, List<SourcedTransaction> sourcedTransactions);
}
