package com.progressoft.jip11.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CSVTransactionsParser implements TransactionsParser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final FieldsValidator validator = new FieldsValidator();

    @Override
    public List<Transaction> parse(ValidPath validPath) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(validPath.getPath())) {
            skipHeader(bufferedReader);
            return getTransactions(bufferedReader);
        } catch (IOException e) {
            throw new TransactionsParserException(e.getMessage(), e);
        }
    }

    private List<Transaction> getTransactions(BufferedReader bufferedReader) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            transactions.add(mapLine(line));
        return transactions;
    }

    private Transaction mapLine(String line) {
        String[] fields = line.split(",");
        validateLineFields(fields);
        return new Transaction.Builder()
                .setId(fields[0])
                .setAmount(new BigDecimal(fields[2]))
                .setCurrency(Currency.getInstance(fields[3]))
                .setDate(LocalDate.parse(fields[5]))
                .build();
    }

    public void validateLineFields(String[] fields) {
        validator.checkNoOfFields(fields);
        validator.validateId(fields[0]);
        validator.validateAmount(fields[2]);
        validator.validateCurrency(fields[3]);
        validator.validateDate(fields[5], formatter);
    }

    private void skipHeader(BufferedReader bufferedReader) throws IOException {
        bufferedReader.readLine();
    }
}
