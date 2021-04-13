package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.ValidPath;
import com.progressoft.jip11.reconciliators.CSVTransactionsImporter;
import com.progressoft.jip11.reconciliators.TransactionsFileImporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStrategy implements ImportStrategy {

    @Override
    public void importTransactions(ImportRequest importRequest) {
        validateRequest(importRequest);
        TransactionsFileImporter importer = new CSVTransactionsImporter();

        Path dirPath = createDirectory();
        String dirPathAsString = dirPath.toString();

        ValidPath matchedPath = new ValidPath(createFile(dirPathAsString, "matched.csv"));
        ValidPath mismatchedPath = new ValidPath(createFile(dirPathAsString, "mismatched.csv"));
        ValidPath missingPath = new ValidPath(createFile(dirPathAsString, "missing.csv"));

        importer.importMatchingTransactions(matchedPath, importRequest.getMatched());
        importer.importOtherTransactions(mismatchedPath, importRequest.getMismatched());
        importer.importOtherTransactions(missingPath, importRequest.getMissing());
    }

    private void validateRequest(ImportRequest importRequest) {
        if (importRequest == null)
            throw new FileStrategyException("import request is null");
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
