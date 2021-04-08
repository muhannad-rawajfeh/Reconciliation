package com.progressoft.jip11.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CSVTransactionsParser implements TransactionsParser {

    @Override
    public Iterable<Transaction> parse(Path path) {
        FileValidator validator = new FileValidator(new CSVFormat());
        validator.validatePath(path);
        return doParse(path, validator);
    }

    private List<Transaction> doParse(Path path, FileValidator validator) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            skipHeader(bufferedReader);
            String line;
            int lineNo = 1;
            while ((line = bufferedReader.readLine()) != null) {
                lineNo++;
                transactions.add(mapLine(validator, line, lineNo));
            }
            return transactions;
        } catch (IOException e) {
            throw new TransactionsParserException(e.getMessage(), e);
        }
    }

    private Transaction mapLine(FileValidator validator, String line, int lineNo) {
        String[] fields = line.split(",");
        validator.validateFields(fields, lineNo);
        return new Transaction.Builder()
                .setId(fields[0])
                .setAmount(new BigDecimal(fields[2]))
                .setCurrency(Currency.getInstance(fields[3]))
                .setDate(LocalDate.parse(fields[5]))
                .build();
    }

    private void skipHeader(BufferedReader bufferedReader) throws IOException {
        bufferedReader.readLine();
    }
}
