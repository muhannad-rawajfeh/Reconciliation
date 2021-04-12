package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.parsers.ValidPath;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CSVTransactionsImporter implements TransactionsImporter {

    private static final String MATCHING_HEADER = "transaction id,amount,currency code,value date\n";
    private static final String OTHER_HEADER = "found in file," + MATCHING_HEADER;

    @Override
    public void importMatchingTransactions(Channel channel, List<Transaction> transactions) {
        doImport(channel, transactions, MATCHING_HEADER);
    }

    @Override
    public void importOtherTransactions(Channel channel, List<SourcedTransaction> sourcedTransactions) {
        doImport(channel, sourcedTransactions, OTHER_HEADER);
    }

    private void doImport(Channel channel, List<?> objects, String header) {
        validateChannelAndList(channel, objects);
        ValidPath validPath = (ValidPath) channel.getCommunicationSource();
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(validPath.getPath())) {
            bufferedWriter.write(header);
            for (Object object : objects) {
                bufferedWriter.write(object.toString() + "\n");
            }
        } catch (IOException e) {
            throw new TransactionsImporterException(e.getMessage(), e);
        }
    }

    private void validateChannelAndList(Channel channel, List<?> transactions) {
        if (channel == null)
            throw new TransactionsImporterException("channel is null");
        if (!(channel instanceof FilePathChannel))
            throw new TransactionsImporterException("invalid communication channel");
        if (transactions == null)
            throw new TransactionsImporterException("list is null");
    }
}
