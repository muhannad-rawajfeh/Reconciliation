package com.progressoft.jip11.parsers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class JSONTransactionsParser implements TransactionsParser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
    private static final ValidatorUtility validator = new ValidatorUtility();

    @Override
    public Iterable<Transaction> parse(ValidPath validPath) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            String fileContent = new String(Files.readAllBytes(validPath.getPath()));
            JSONArray jsonArray = new JSONArray(fileContent);
            jsonArray.forEach(o -> mapAndAdd(transactions, (JSONObject) o));
            return transactions;
        } catch (IOException e) {
            throw new TransactionsParserException(e.getMessage(), e);
        }
    }

    private void mapAndAdd(List<Transaction> transactions, JSONObject o) {
        validateObjectFields(o);
        String id = o.getString("reference");
        BigDecimal amount = o.getBigDecimal("amount");
        Currency currency = Currency.getInstance(o.getString("currencyCode"));
        LocalDate date = LocalDate.parse(o.getString("date"), formatter);
        Transaction transaction = new Transaction.Builder()
                .setId(id)
                .setAmount(amount)
                .setCurrency(currency)
                .setDate(date)
                .build();
        transactions.add(transaction);
    }

    private void validateObjectFields(JSONObject o) {
        validator.checkMissingFields(o);
        validator.validateId(o.getString("reference"));
        validator.validateAmount(o.getString("amount"));
        validator.validateCurrency(o.getString("currencyCode"));
        validator.validateDate(o.getString("date"), formatter);
    }
}
