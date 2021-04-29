package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.reconciliators.TransactionsWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTransactionsExporter implements TransactionsExporter {

    private final TransactionsWriter transactionsWriter;

    public FileTransactionsExporter(TransactionsWriter transactionsWriter) {
        this.transactionsWriter = transactionsWriter;
    }

    @Override
    public void exportTransactions(ExportRequest exportRequest) {
        validateRequest(exportRequest);

        Path dirPath = createDirectory();
        String dirPathAsString = dirPath.toString();

        String extension = transactionsWriter.getExtension();
        FilePath matchedPath = new FilePath(createFile(dirPathAsString, "matched" + extension));
        FilePath mismatchedPath = new FilePath(createFile(dirPathAsString, "mismatched" + extension));
        FilePath missingPath = new FilePath(createFile(dirPathAsString, "missing" + extension));

        transactionsWriter.writeMatched(matchedPath, exportRequest.getMatched());
        transactionsWriter.writeOther(mismatchedPath, exportRequest.getMismatched());
        transactionsWriter.writeOther(missingPath, exportRequest.getMissing());
    }

    private void validateRequest(ExportRequest exportRequest) {
        if (exportRequest == null)
            throw new TransactionsExporterException("import request is null");
    }

    private Path createFile(String dirPathAsString, String name) {
        try {
            return Files.createFile(Paths.get(dirPathAsString, name));
        } catch (IOException e) {
            throw new TransactionsExporterException("error occurred while creating result files", e);
        }
    }

    private Path createDirectory() {
        try {
            return Files.createDirectory(Paths.get("reconciliation-results"));
        } catch (IOException e) {
            throw new TransactionsExporterException("error occurred while creating results directory", e);
        }
    }
}
