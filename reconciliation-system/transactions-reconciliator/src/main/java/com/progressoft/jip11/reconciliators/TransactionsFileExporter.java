package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.parsers.ValidPath;

import java.util.List;

public interface TransactionsFileExporter {

    void exportMatchingTransactions(ValidPath validPath, List<Transaction> transactions);

    void exportOtherTransactions(ValidPath validPath, List<SourcedTransaction> sourcedTransactions);
}
