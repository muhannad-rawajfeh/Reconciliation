package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.parsers.TransactionsParser;
import com.progressoft.jip11.parsers.ValidPath;
import com.progressoft.jip11.reconciliators.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class ReconciliationSystem {

    private final TransactionsParser sourceParser;
    private final TransactionsParser targetParser;
    private final ImportStrategy importStrategy;

    public ReconciliationSystem(TransactionsParser sourceParser, TransactionsParser targetParser, ImportStrategy importStrategy) {
        this.sourceParser = sourceParser;
        this.targetParser = targetParser;
        this.importStrategy = importStrategy;
    }

    public void reconcile(ValidPath sourcePath, ValidPath targetPath) {
        List<Transaction> sourceTransactions = sourceParser.parse(sourcePath);
        List<Transaction> targetTransactions = targetParser.parse(targetPath);

        TransactionsReconciliator reconciliator = new TransactionsReconciliator();
        List<Transaction> matchedTransactions = reconciliator.findMatching(sourceTransactions, targetTransactions);
        List<SourcedTransaction> mismatchedTransactions = reconciliator.findMismatching(sourceTransactions, targetTransactions);
        List<SourcedTransaction> missingTransactions = reconciliator.wrapMissing(sourceTransactions, targetTransactions);

        ImportRequest importRequest = new ImportRequest(matchedTransactions, mismatchedTransactions, missingTransactions);
        importStrategy.importTransactions(importRequest);
    }
}
