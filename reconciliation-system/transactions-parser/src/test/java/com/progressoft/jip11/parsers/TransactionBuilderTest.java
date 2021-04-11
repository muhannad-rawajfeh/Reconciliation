package com.progressoft.jip11.parsers;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class TransactionBuilderTest {

    @Test
    void givenTransactionFields_whenBuild_thenBuildCorrectlyWithAmountFormattedAsPerItsCurrencyCode() {
        String id = "TR-12345678912";
        BigDecimal amount = new BigDecimal("100");
        Currency currency = Currency.getInstance("JOD");
        LocalDate date = LocalDate.parse("2020-06-10");

        Transaction transaction = new Transaction.Builder()
                .setId(id)
                .setAmount(amount)
                .setCurrency(currency)
                .setDate(date)
                .build();

        assertEquals(id, transaction.getId());
        assertEquals(new BigDecimal("100.000"), transaction.getAmount());
        assertEquals(currency, transaction.getCurrency());
        assertEquals(date, transaction.getDate());

        currency = Currency.getInstance("USD");

        Transaction transaction2 = new Transaction.Builder()
                .setId(id)
                .setAmount(amount)
                .setCurrency(currency)
                .setDate(date)
                .build();

        assertEquals(id, transaction2.getId());
        assertEquals(new BigDecimal("100.00"), transaction2.getAmount());
        assertEquals(currency, transaction2.getCurrency());
        assertEquals(date, transaction2.getDate());
    }
}