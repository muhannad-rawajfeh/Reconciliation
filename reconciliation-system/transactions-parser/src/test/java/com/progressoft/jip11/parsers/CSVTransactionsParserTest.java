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

class CSVTransactionsParserTest {

    private TransactionsParser transactionsParser;

    @BeforeEach
    void setUp() {
        transactionsParser = new CSVTransactionsParser();
    }

    @Test
    void givenFileWithAnInvalidNoOfFieldsRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "csv", "invalid-no-of-fields.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new FilePath(path)));

        assertEquals("invalid number of fields found: [TR-47884222206, 500.0, USD, , D]", tpe.getMessage());
    }

    @Test
    void givenFileWithAnInvalidIDRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "csv", "invalid-id.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new FilePath(path)));

        assertEquals("invalid id/reference found: TR-4788422", tpe.getMessage());
    }

    @Test
    void givenFileWithAnInvalidAmountRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "csv", "invalid-amount-format.csv");
        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new FilePath(path)));
        assertEquals("invalid amount format found: 1200.a00", tpe.getMessage());

        Path path2 = Paths.get("src", "test", "resources", "csv/invalid-amount-value.csv");
        TransactionsParserException tpe2 = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new FilePath(path2)));
        assertEquals("invalid amount value found: -60.00", tpe2.getMessage());
    }

    @Test
    void givenFileWithAnInvalidCurrencyRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "csv", "invalid-currency.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new FilePath(path)));

        assertEquals("invalid currency found: JODas", tpe.getMessage());
    }

    @Test
    void givenFileWithAnInvalidDateRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "csv", "invalid-date.csv");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new FilePath(path)));

        assertEquals("invalid date found: 25-01-2020", tpe.getMessage());
    }

    @Test
    void givenValidFile_whenParse_thenReturnListOfTransactionsCorrectly() {
        Path path = Paths.get("src", "test", "resources", "csv", "valid-file.csv");

        List<Transaction> result = transactionsParser.parse(new FilePath(path));

        List<Transaction> expected = prepareExpected();

        assertEquals(expected, result);
    }

    private List<Transaction> prepareExpected() {
        return Arrays.asList(
                new Transaction.Builder()
                        .setId("TR-47884222201")
                        .setAmount(new BigDecimal("140"))
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
