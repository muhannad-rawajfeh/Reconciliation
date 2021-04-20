package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.reconciliators.CSVWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStrategy implements ExportStrategy {

    @Override
    public void exportTransactions(ExportRequest exportRequest) {
        validateRequest(exportRequest);
        CSVWriter csvWriter = new CSVWriter();

        Path dirPath = createDirectory();
        String dirPathAsString = dirPath.toString();

        FilePath matchedPath = new FilePath(createFile(dirPathAsString, "matched.csv"));
        FilePath mismatchedPath = new FilePath(createFile(dirPathAsString, "mismatched.csv"));
        FilePath missingPath = new FilePath(createFile(dirPathAsString, "missing.csv"));

        csvWriter.writeMatching(matchedPath, exportRequest.getMatched());
        csvWriter.writeOther(mismatchedPath, exportRequest.getMismatched());
        csvWriter.writeOther(missingPath, exportRequest.getMissing());
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
