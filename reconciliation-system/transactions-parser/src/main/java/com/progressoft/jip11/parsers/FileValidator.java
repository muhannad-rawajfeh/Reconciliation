package com.progressoft.jip11.parsers;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;

public class FileValidator {

    private final DataFormat dataFormat;

    public FileValidator(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public void validatePath(Path path) {
        if (path == null) {
            throw new TransactionsParserException("path is null");
        }
        if (Files.notExists(path)) {
            throw new TransactionsParserException("path does not exist");
        }
        if (Files.isDirectory(path)) {
            throw new TransactionsParserException("path is a directory");
        }
    }

    public void validateFields(String[] fields, int lineNo) {
        validateNoOfFields(fields.length, lineNo);
        validateId(fields[0], lineNo);
        validateAmount(fields[2], lineNo);
        validateCurrency(fields[3], lineNo);
        validateDate(fields[5], lineNo);
    }

    private void validateDate(String field, int lineNo) {
        try {
            LocalDate.parse(field, dataFormat.dateFormat());
        } catch (DateTimeParseException e) {
            throw new TransactionsParserException("invalid date in line " + lineNo);
        }
    }

    private void validateCurrency(String field, int lineNo) {
        try {
            Currency.getInstance(field);
        } catch (Exception e) {
            throw new TransactionsParserException("invalid currency in line " + lineNo);
        }
    }

    private void validateAmount(String field, int lineNo) {
        try {
            new BigDecimal(field);
        } catch (NumberFormatException e) {
            throw new TransactionsParserException("invalid amount in line " + lineNo);
        }
    }

    private void validateId(String id, int lineNo) {
        if (!id.matches(DataFormat.idPattern())) {
            throw new TransactionsParserException("invalid id in line " + lineNo);
        }
    }

    private void validateNoOfFields(int fieldsLength, int lineNo) {
        if (fieldsLength != dataFormat.numberOfFields()) {
            throw new TransactionsParserException("invalid number of fields in line " + lineNo);
        }
    }

}
