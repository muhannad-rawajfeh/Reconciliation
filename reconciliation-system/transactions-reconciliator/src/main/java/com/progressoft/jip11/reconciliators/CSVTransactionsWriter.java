package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.parsers.Transaction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CSVTransactionsWriter implements TransactionsWriter {

    private static final String MATCHED_HEADER = "transaction id,amount,currency code,value date\n";
    private static final String OTHER_HEADER = "found in file," + MATCHED_HEADER;

    @Override
    public void writeMatched(FilePath filePath, List<Transaction> transactions) {
        doWrite(filePath, transactions, MATCHED_HEADER);
    }

    @Override
    public void writeOther(FilePath filePath, List<SourcedTransaction> sourcedTransactions) {
        doWrite(filePath, sourcedTransactions, OTHER_HEADER);
    }

    @Override
    public String getExtension() {
        return ".csv";
    }

    public void doWrite(FilePath filePath, List<?> objects, String header) {
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
