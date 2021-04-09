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

public class JSONTransactionsParserTest {

    private TransactionsParser transactionsParser;

    @BeforeEach
    void setUp() {
        transactionsParser = new JSONTransactionsParser();
    }

    @Test
    void givenFileWithAnObjectWithMissingMandatoryField_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "mandatory-field-missing.json");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("mandatory field missing: JSONObject[\"reference\"] not found.", tpe.getMessage());
    }

    @Test
    void givenFileWithAnObjectWithInvalidReference_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-reference.json");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid id/reference found: TR-4788422aa", tpe.getMessage());
    }

    @Test
    void givenFileWithAnObjectWithInvalidAmount_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-amount.json");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid amount found: 140.p", tpe.getMessage());
    }

    @Test
    void givenFileWithAnObjectWithInvalidCurrency_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-currency.json");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid currency found: USDas", tpe.getMessage());
    }

    @Test
    void givenFileWithAnObjectWithInvalidDate_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-date.json");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid date found: 2020-02-10", tpe.getMessage());
    }

    @Test
    void givenValidFile_whenParse_thenReturnListOfTransactions() {
        Path path = Paths.get("src", "test", "resources", "valid-file.json");

        List<Transaction> result = (List<Transaction>) transactionsParser.parse(new ValidPath(path));

        List<Transaction> expected = prepareCase();

        assertEquals(expected, result);
    }

    private List<Transaction> prepareCase() {
        return Arrays.asList(
                new Transaction.Builder()
                        .setId("TR-47884222201")
                        .setAmount(new BigDecimal("140.00"))
                        .setCurrency(Currency.getInstance("USD"))
                        .setDate(LocalDate.parse("2020-01-20"))
                        .build(),
                new Transaction.Builder()
                        .setId("TR-47884222205")
                        .setAmount(new BigDecimal("60.000"))
                        .setCurrency(Currency.getInstance("JOD"))
                        .setDate(LocalDate.parse("2020-02-03"))
                        .build()
        );
    }
}
