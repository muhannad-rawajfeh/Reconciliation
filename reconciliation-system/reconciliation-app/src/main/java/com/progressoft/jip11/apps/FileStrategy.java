package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.ValidPath;
import com.progressoft.jip11.reconciliators.CSVTransactionsExporter;
import com.progressoft.jip11.reconciliators.TransactionsFileExporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStrategy implements ExportStrategy {

    @Override
    public void exportTransactions(ExportRequest exportRequest) {
        validateRequest(exportRequest);
        TransactionsFileExporter importer = new CSVTransactionsExporter();

        Path dirPath = createDirectory();
        String dirPathAsString = dirPath.toString();

        ValidPath matchedPath = new ValidPath(createFile(dirPathAsString, "matched.csv"));
        ValidPath mismatchedPath = new ValidPath(createFile(dirPathAsString, "mismatched.csv"));
        ValidPath missingPath = new ValidPath(createFile(dirPathAsString, "missing.csv"));

        importer.exportMatchingTransactions(matchedPath, exportRequest.getMatched());
        importer.exportOtherTransactions(mismatchedPath, exportRequest.getMismatched());
        importer.exportOtherTransactions(missingPath, exportRequest.getMissing());
    }

    private void validateRequest(ExportRequest exportRequest) {
        if (exportRequest == null)
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
