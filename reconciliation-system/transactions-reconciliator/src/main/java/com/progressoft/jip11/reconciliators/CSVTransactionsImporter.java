package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.parsers.ValidPath;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

// TODO this is exporter not importer
public class CSVTransactionsImporter implements TransactionsFileImporter {

    private static final String MATCHING_HEADER = "transaction id,amount,currency code,value date\n";
    private static final String OTHER_HEADER = "found in file," + MATCHING_HEADER;

    @Override
    public void importMatchingTransactions(ValidPath validPath, List<Transaction> transactions) {
        doImport(validPath, transactions, MATCHING_HEADER);
    }

    @Override
    public void importOtherTransactions(ValidPath validPath, List<SourcedTransaction> sourcedTransactions) {
        doImport(validPath, sourcedTransactions, OTHER_HEADER);
    }

    private void doImport(ValidPath validPath, List<?> objects, String header) {
        validatePathAndList(validPath, objects);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(validPath.getPath())) {
            bufferedWriter.write(header);
            for (Object object : objects) {
                bufferedWriter.write(object.toString() + "\n");
            }
        } catch (IOException e) {
            throw new TransactionsImporterException(e.getMessage(), e);
        }
    }

    private void validatePathAndList(ValidPath validPath, List<?> objects) {
        if (validPath == null)
            throw new TransactionsImporterException("path is null");
        if (objects == null)
            throw new TransactionsImporterException("list is null");
    }
}
