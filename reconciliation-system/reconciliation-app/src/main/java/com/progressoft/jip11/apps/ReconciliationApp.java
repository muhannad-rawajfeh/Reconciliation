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

public class ReconciliationApp {

    private final TransactionsParser sourceParser;
    private final TransactionsParser targetParser;
    private final TransactionsImporter importer;

    public ReconciliationApp(TransactionsParser sourceParser, TransactionsParser targetParser, TransactionsImporter importer) {
        this.sourceParser = sourceParser;
        this.targetParser = targetParser;
        this.importer = importer;
    }

    public Channel reconcile(ValidPath sourcePath, ValidPath targetPath) {
        List<Transaction> sourceTransactions = sourceParser.parse(sourcePath);
        List<Transaction> targetTransactions = targetParser.parse(targetPath);

        TransactionsReconciliator reconciliator = new TransactionsReconciliator();
        List<Transaction> matchedTransactions = reconciliator.findMatching(sourceTransactions, targetTransactions);
        List<SourcedTransaction> mismatchedTransactions = reconciliator.findMismatching(sourceTransactions, targetTransactions);
        List<SourcedTransaction> missingTransactions = reconciliator.wrapMissing(sourceTransactions, targetTransactions);

        ImportRequest importRequest = new ImportRequest(matchedTransactions, mismatchedTransactions, missingTransactions);
        return importAndGetChannelToResult(importRequest);
    }

    private FilePathChannel importAndGetChannelToResult(ImportRequest importRequest) {
        Path dirPath = createDirectory();
        String dirPathAsString = dirPath.toString();
        Path matched = createFile(dirPathAsString, "matched.csv");
        Path mismatched = createFile(dirPathAsString, "mismatched.csv");
        Path missing = createFile(dirPathAsString, "missing.csv");

        Channel channelToMatched = new FilePathChannel(new ValidPath(matched));
        Channel channelToMismatched = new FilePathChannel(new ValidPath(mismatched));
        Channel channelToMissing = new FilePathChannel(new ValidPath(missing));

        importer.importMatchingTransactions(channelToMatched, importRequest.getMatched());
        importer.importOtherTransactions(channelToMismatched, importRequest.getMismatched());
        importer.importOtherTransactions(channelToMissing, importRequest.getMissing());
        return new FilePathChannel(new ValidPath(dirPath));
    }

    private Path createFile(String dirPathAsString, String name) {
        try {
            return Files.createFile(Paths.get(dirPathAsString, name));
        } catch (IOException e) {
            throw new ReconciliationAppException("error occurred while creating result files", e);
        }
    }

    private Path createDirectory() {
        try {
            return Files.createDirectory(Paths.get("reconciliation-results" + new Random().nextInt()));
        } catch (IOException e) {
            throw new ReconciliationAppException("error occurred while creating results directory", e);
        }
    }
}
