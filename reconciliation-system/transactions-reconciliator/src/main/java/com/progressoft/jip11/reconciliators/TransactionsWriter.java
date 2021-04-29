package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.parsers.Transaction;

import java.util.List;

public interface TransactionsWriter {

    void writeMatched(FilePath filePath, List<Transaction> transactions);

    void writeOther(FilePath filePath, List<SourcedTransaction> sourcedTransactions);

    String getExtension();
}
