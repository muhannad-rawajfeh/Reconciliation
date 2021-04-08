package com.progressoft.jip11.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVTransactionsParserTest {

    private TransactionsParser transactionsParser;

    @BeforeEach
    void setUp() {
        transactionsParser = new CSVTransactionsParser();
    }

    @Test
    void givenNullPath_whenParse_thenFail() {
        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(null));

        assertEquals("path is null", tpe.getMessage());
    }

    @Test
    void givenNoneExistingPath_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "foo" + new Random().nextInt() + ".test");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(path));

        assertEquals("path does not exist", tpe.getMessage());
    }

    @Test
    void givenDirectoryPath_whenParse_thenFail() throws IOException {
        Path path = Files.createTempDirectory("temp");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(path));

        assertEquals("path is a directory", tpe.getMessage());
    }

    @Test
    void givenFileWithInvalidNoOfFieldsRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-no-of-fields.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(path));

        assertEquals("invalid number of fields in line 7", tpe.getMessage());
    }

    @Test
    void givenFileWithInvalidIDRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-id.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(path));

        assertEquals("invalid id in line 3", tpe.getMessage());
    }

    @Test
    void givenFileWithInvalidAmountRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-amount.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(path));

        assertEquals("invalid amount in line 5", tpe.getMessage());
    }

    @Test
    void givenFileWithInvalidCurrencyRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-currency.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(path));

        assertEquals("invalid currency in line 6", tpe.getMessage());
    }

    @Test
    void givenFileWithInvalidDateRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-date.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(path));

        assertEquals("invalid date in line 4", tpe.getMessage());
    }

    @Test
    void givenValidFile_whenParse_thenReturnListOfTransactions() {
        Path path = Paths.get("src", "test", "resources", "valid-file.csv");

        List<Transaction> result = (List<Transaction>) transactionsParser.parse(path);

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
