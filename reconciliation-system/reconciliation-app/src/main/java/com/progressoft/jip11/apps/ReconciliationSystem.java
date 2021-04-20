package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.parsers.TransactionsParser;
import com.progressoft.jip11.reconciliators.SourcedTransaction;
import com.progressoft.jip11.reconciliators.TransactionsReconciliator;

import java.util.List;

public class ReconciliationSystem {

    private final TransactionsParser sourceParser;
    private final TransactionsParser targetParser;
    private final TransactionsExporter transactionsExporter;

    public ReconciliationSystem(TransactionsParser sourceParser, TransactionsParser targetParser, TransactionsExporter transactionsExporter) {
        this.sourceParser = sourceParser;
        this.targetParser = targetParser;
        this.transactionsExporter = transactionsExporter;
    }

    public void reconcile(FilePath sourcePath, FilePath targetPath) {
        List<Transaction> sourceTransactions = sourceParser.parse(sourcePath);
        List<Transaction> targetTransactions = targetParser.parse(targetPath);

        TransactionsReconciliator reconciliator = new TransactionsReconciliator();
        List<Transaction> matchedTransactions = reconciliator.findMatched(sourceTransactions, targetTransactions);
        List<SourcedTransaction> mismatchedTransactions = reconciliator.findMismatched(sourceTransactions, targetTransactions);
        List<SourcedTransaction> missingTransactions = reconciliator.wrapMissing(sourceTransactions, targetTransactions);

        ExportRequest exportRequest = new ExportRequest(matchedTransactions, mismatchedTransactions, missingTransactions);
        transactionsExporter.exportTransactions(exportRequest);
    }
}
