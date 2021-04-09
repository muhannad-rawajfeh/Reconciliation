package com.progressoft.jip11.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVTransactionsParserTest {

    private TransactionsParser transactionsParser;

    @BeforeEach
    void setUp() {
        transactionsParser = new CSVTransactionsParser();
    }

    @Test
    void givenFileWithInvalidNoOfFieldsRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-no-of-fields.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid number of fields in line 7", tpe.getMessage());
    }

    @Test
    void givenFileWithInvalidIDRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-id.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid id in line 3", tpe.getMessage());
    }

    @Test
    void givenFileWithInvalidAmountRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-amount.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid amount in line 5", tpe.getMessage());
    }

    @Test
    void givenFileWithInvalidCurrencyRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-currency.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid currency in line 6", tpe.getMessage());
    }

    @Test
    void givenFileWithInvalidDateRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-date.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid date in line 4", tpe.getMessage());
    }

    @Test
    void givenValidFile_whenParse_thenReturnListOfTransactions() {
        Path path = Paths.get("src", "test", "resources", "valid-file.csv");

        List<Transaction> result = (List<Transaction>) transactionsParser.parse(new ValidPath(path));

        List<Transaction> expected = prepareCase();

        assertEquals(expected, result);
    }

    private List<Transaction> prepareCase() {
        return Arrays.asList(
                new Transaction.Builder()
                        .setId("TR-47884222201")
                        .setAmount(BigDecimal.valueOf(140))
                        .setCurrency(Currency.getInstance("USD"))
                        .setDate(LocalDate.parse("2020-01-20"))
                        .build(),
                new Transaction.Builder()
                        .setId("TR-47884222202")
                        .setAmount(new BigDecimal("20.0000"))
                        .setCurrency(Currency.getInstance("JOD"))
                        .setDate(LocalDate.parse("2020-01-22"))
                        .build()
        );
    }
}
