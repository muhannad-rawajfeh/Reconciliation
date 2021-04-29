package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.parsers.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class JSONTransactionsWriter implements TransactionsWriter {

    @Override
    public void writeMatched(FilePath filePath, List<Transaction> transactions) {
        validatePathAndList(filePath, transactions);
        JSONArray jsonArray = new JSONArray();
        fillMatched(transactions, jsonArray);
        doWrite(filePath, jsonArray);
    }

    @Override
    public void writeOther(FilePath filePath, List<SourcedTransaction> sourcedTransactions) {
        validatePathAndList(filePath, sourcedTransactions);
        JSONArray jsonArray = new JSONArray();
        fillOther(sourcedTransactions, jsonArray);
        doWrite(filePath, jsonArray);
    }

    @Override
    public String getExtension() {
        return ".json";
    }

    private void doWrite(FilePath filePath, JSONArray jsonArray) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath.getPath())) {
            bufferedWriter.write(jsonArray.toString(2));
        } catch (IOException e) {
            throw new TransactionsWriterException(e.getMessage(), e);
        }
    }

    private void fillOther(List<SourcedTransaction> sourcedTransactions, JSONArray jsonArray) {
        for (SourcedTransaction st : sourcedTransactions) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("found in file", st.getSource());
            jsonObject.put("id", st.getTransaction().getId());
            jsonObject.put("amount", st.getTransaction().getAmount().toString());
            jsonObject.put("currency", st.getTransaction().getCurrency());
            jsonObject.put("date", st.getTransaction().getDate());
            jsonArray.put(jsonObject);
        }
    }

    private void fillMatched(List<Transaction> transactions, JSONArray jsonArray) {
        for (Transaction t : transactions) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", t.getId());
            jsonObject.put("amount", t.getAmount().toString());
            jsonObject.put("currency", t.getCurrency());
            jsonObject.put("date", t.getDate());
            jsonArray.put(jsonObject);
        }
    }

    private void validatePathAndList(FilePath filePath, List<?> objects) {
        if (filePath == null)
            throw new TransactionsWriterException("path is null");
        if (objects == null)
            throw new TransactionsWriterException("list is null");
    }
}
