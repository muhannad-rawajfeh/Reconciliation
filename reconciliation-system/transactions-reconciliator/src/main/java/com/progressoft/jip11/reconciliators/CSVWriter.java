package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.parsers.Transaction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CSVWriter implements TransactionsWriter {

    private static final String MATCHING_HEADER = "transaction id,amount,currency code,value date\n";
    private static final String OTHER_HEADER = "found in file," + MATCHING_HEADER;

    @Override
    public void writeMatching(FilePath filePath, List<Transaction> transactions) {
        doWrite(filePath, transactions, MATCHING_HEADER);
    }

    @Override
    public void writeOther(FilePath filePath, List<SourcedTransaction> sourcedTransactions) {
        doWrite(filePath, sourcedTransactions, OTHER_HEADER);
    }

    public <T> void doWrite(FilePath filePath, List<T> objects, String header) {
        validatePathAndList(filePath, objects);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath.getPath())) {
            bufferedWriter.write(header);
            for (Object object : objects) {
                bufferedWriter.write(object.toString() + "\n");
            }
        } catch (IOException e) {
            throw new TransactionsWriterException(e.getMessage(), e);
        }
    }

    private void validatePathAndList(FilePath filePath, List<?> objects) {
        if (filePath == null)
            throw new TransactionsWriterException("path is null");
        if (objects == null)
            throw new TransactionsWriterException("list is null");
    }
}
