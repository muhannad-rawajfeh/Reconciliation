package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.parsers.ValidPath;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CSVTransactionsExporter implements TransactionsFileExporter {

    private static final String MATCHING_HEADER = "transaction id,amount,currency code,value date\n";
    private static final String OTHER_HEADER = "found in file," + MATCHING_HEADER;

    @Override
    public void exportMatchingTransactions(ValidPath validPath, List<Transaction> transactions) {
        doExport(validPath, transactions, MATCHING_HEADER);
    }

    @Override
    public void exportOtherTransactions(ValidPath validPath, List<SourcedTransaction> sourcedTransactions) {
        doExport(validPath, sourcedTransactions, OTHER_HEADER);
    }

    private void doExport(ValidPath validPath, List<?> objects, String header) {
        validatePathAndList(validPath, objects);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(validPath.getPath())) {
            bufferedWriter.write(header);
            for (Object object : objects) {
                bufferedWriter.write(object.toString() + "\n");
            }
        } catch (IOException e) {
            throw new TransactionsExporterException(e.getMessage(), e);
        }
    }

    private void validatePathAndList(ValidPath validPath, List<?> objects) {
        if (validPath == null)
            throw new TransactionsExporterException("path is null");
        if (objects == null)
            throw new TransactionsExporterException("list is null");
    }
}
