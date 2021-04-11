package com.progressoft.jip11.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Currency;

public class FieldsValidator {

    private static final int NO_OF_FIELDS = 7;
    private static final String ID_REGEX = "TR-\\d{11}";

    public void validateDate(String date, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new TransactionsParserException("invalid date found: " + date);
        }
    }

    public void validateCurrency(String currency) {
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new TransactionsParserException("invalid currency found: " + currency);
        }
    }

    public void validateAmount(String amount) {
        try {
            BigDecimal val = new BigDecimal(amount);
            if (val.compareTo(BigDecimal.ZERO) <= 0)
                throw new TransactionsParserException("invalid amount value found: " + amount);
        } catch (NumberFormatException e) {
            throw new TransactionsParserException("invalid amount format found: " + amount);
        }
    }

    public void validateId(String id) {
        if (!id.matches(ID_REGEX))
            throw new TransactionsParserException("invalid id/reference found: " + id);
    }

    public void checkMissingFields(JSONObject o) {
        try {
            o.get("reference");
            o.get("amount");
            o.get("currencyCode");
            o.get("date");
        } catch (JSONException e) {
            throw new TransactionsParserException("mandatory field missing: " + e.getMessage());
        }
    }

    public void checkNoOfFields(String[] fields) {
        if (fields.length != NO_OF_FIELDS)
            throw new TransactionsParserException("invalid number of fields found: " + Arrays.toString(fields));
    }
}
