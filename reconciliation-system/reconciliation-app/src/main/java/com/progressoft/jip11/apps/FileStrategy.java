package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.ValidPath;
import com.progressoft.jip11.reconciliators.CSVFileWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStrategy implements ExportStrategy {

    private static final String MATCHING_HEADER = "transaction id,amount,currency code,value date\n";
    private static final String OTHER_HEADER = "found in file," + MATCHING_HEADER;

    @Override
    public void exportTransactions(ExportRequest exportRequest) {
        validateRequest(exportRequest);
        CSVFileWriter csvFileWriter = new CSVFileWriter();

        Path dirPath = createDirectory();
        String dirPathAsString = dirPath.toString();

        ValidPath matchedPath = new ValidPath(createFile(dirPathAsString, "matched.csv"));
        ValidPath mismatchedPath = new ValidPath(createFile(dirPathAsString, "mismatched.csv"));
        ValidPath missingPath = new ValidPath(createFile(dirPathAsString, "missing.csv"));

        csvFileWriter.write(matchedPath, exportRequest.getMatched(), MATCHING_HEADER);
        csvFileWriter.write(mismatchedPath, exportRequest.getMismatched(), OTHER_HEADER);
        csvFileWriter.write(missingPath, exportRequest.getMissing(), OTHER_HEADER);
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
