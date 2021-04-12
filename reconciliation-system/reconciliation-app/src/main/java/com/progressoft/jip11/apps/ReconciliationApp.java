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

public class ReconciliationApp {

    private final TransactionsParser sourceParser;
    private final TransactionsParser targetParser;
    private final TransactionsImporter importer;

    public ReconciliationApp(TransactionsParser sourceParser, TransactionsParser targetParser, TransactionsImporter importer) {
        this.sourceParser = sourceParser;
        this.targetParser = targetParser;
        this.importer = importer;
    }

    public void run(Path sourcePath, Path targetPath) {

        List<Transaction> sourceTransactions = sourceParser.parse(new ValidPath(sourcePath));
        List<Transaction> targetTransactions = targetParser.parse(new ValidPath(targetPath));

        TransactionsReconciliator reconciliator = new TransactionsReconciliator();

        List<Transaction> matchedTransactions = reconciliator.findMatching(sourceTransactions, targetTransactions);
        List<SourcedTransaction> mismatchedTransactions = reconciliator.findMismatching(sourceTransactions, targetTransactions);
        List<SourcedTransaction> missingTransactions = reconciliator.wrapMissing(sourceTransactions, targetTransactions);

        Path dirPath;
        try {
            dirPath = Files.createDirectory(Paths.get(".", "reconciliation-results"));
        } catch (IOException e) {
            System.err.println("error occurred while creating directory for results: " + e.getMessage());
            return;
        }
        String dirPathAsString = dirPath.toString();

        Path matched;
        Path mismatched;
        Path missing;
        try {
            matched = Files.createFile(Paths.get(dirPathAsString, "matched.csv"));
            mismatched = Files.createFile(Paths.get(dirPathAsString, "mismatched.csv"));
            missing = Files.createFile(Paths.get(dirPathAsString, "missing.csv"));
        } catch (IOException e) {
            System.err.println("error occurred while creating result files: " + e.getMessage());
            return;
        }

        Channel channelToMatched = new FilePathChannel(new ValidPath(matched));
        Channel channelToMismatched = new FilePathChannel(new ValidPath(mismatched));
        Channel channelToMissing = new FilePathChannel(new ValidPath(missing));

        importer.importMatchingTransactions(channelToMatched, matchedTransactions);
        importer.importOtherTransactions(channelToMismatched, mismatchedTransactions);
        importer.importOtherTransactions(channelToMissing, missingTransactions);

        System.out.println("Reconciliation finished.");
        System.out.println("Result files are available in " + dirPath.toAbsolutePath());
    }
}
