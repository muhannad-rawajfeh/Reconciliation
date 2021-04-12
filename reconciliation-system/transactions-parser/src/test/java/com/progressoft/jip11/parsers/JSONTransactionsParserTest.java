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

class JSONTransactionsParserTest {

    private TransactionsParser transactionsParser;

    @BeforeEach
    void setUp() {
        transactionsParser = new JSONTransactionsParser();
    }

    @Test
    void givenFileWithAnObjectWithMissingMandatoryField_whenParse_thenFail() { // TODO: add all cases
        Path path = Paths.get("src", "test", "resources", "json", "mandatory-field-missing.json");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("mandatory field missing: JSONObject[\"reference\"] not found.", tpe.getMessage());
    }

    @Test
    void givenFileWithAnObjectWithInvalidReference_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "json", "invalid-reference.json");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid id/reference found: TR-4788422aa", tpe.getMessage());
    }

    @Test
    void givenFileWithAnObjectWithInvalidAmount_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "json", "invalid-amount-format.json");
        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));
        assertEquals("invalid amount format found: 140.p", tpe.getMessage());

        Path path2 = Paths.get("src", "test", "resources", "json/invalid-amount-value.json");
        TransactionsParserException tpe2 = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path2)));
        assertEquals("invalid amount value found: 0.00", tpe2.getMessage());
    }

    @Test
    void givenFileWithAnObjectWithInvalidCurrency_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "json", "invalid-currency.json");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid currency found: USDas", tpe.getMessage());
    }

    @Test
    void givenFileWithAnObjectWithInvalidDate_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "json", "invalid-date.json");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid date found: 2020-02-10", tpe.getMessage());
    }

    @Test
    void givenValidFile_whenParse_thenReturnListOfTransactionsCorrectly() {
        Path path = Paths.get("src", "test", "resources", "json", "valid-file.json");

        List<Transaction> result = transactionsParser.parse(new ValidPath(path));

        List<Transaction> expected = prepareExpected();

        assertEquals(expected, result);
    }

    private List<Transaction> prepareExpected() {
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
