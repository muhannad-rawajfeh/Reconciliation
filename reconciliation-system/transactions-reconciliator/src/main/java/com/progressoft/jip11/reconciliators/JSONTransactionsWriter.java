package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.parsers.Transaction;

import java.util.List;

public class JSONTransactionsWriter implements TransactionsWriter {

    @Override
    public void writeMatched(FilePath filePath, List<Transaction> transactions) {
    }

    @Override
    public void writeOther(FilePath filePath, List<SourcedTransaction> sourcedTransactions) {
    }
}
