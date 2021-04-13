package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.ValidPath;
import com.progressoft.jip11.reconciliators.CSVTransactionsImporter;
import com.progressoft.jip11.reconciliators.Channel;
import com.progressoft.jip11.reconciliators.FilePathChannel;
import com.progressoft.jip11.reconciliators.TransactionsImporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStrategy implements ImportStrategy {

    @Override
    public void importTransactions(ImportRequest importRequest) {
        TransactionsImporter importer = new CSVTransactionsImporter();

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
    }

    private Path createFile(String dirPathAsString, String name) {
        try {
            return Files.createFile(Paths.get(dirPathAsString, name));
        } catch (IOException e) {
            throw new FileStrategyException("error occurred while creating result files", e);
        }
    }

    private Path createDirectory() {
        try {
            return Files.createDirectory(Paths.get("reconciliation-results"));
        } catch (IOException e) {
            throw new FileStrategyException("error occurred while creating results directory", e);
        }
    }
}
