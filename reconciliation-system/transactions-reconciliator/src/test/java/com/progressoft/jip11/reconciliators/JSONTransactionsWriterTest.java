package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.parsers.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JSONTransactionsWriterTest {

    private TransactionsWriter transactionsWriter;

    @BeforeEach
    void setUp() {
        transactionsWriter = new JSONTransactionsWriter();
    }

    @Test
    void givenNullPath_whenWriteMatched_thenFail() {
        TransactionsWriterException thrown = assertThrows(TransactionsWriterException.class,
                () -> transactionsWriter.writeMatched(null, new ArrayList<>()));

        assertEquals("path is null", thrown.getMessage());
    }

    @Test
    void givenNullPath_whenWriteOther_thenFail() {
        TransactionsWriterException thrown = assertThrows(TransactionsWriterException.class,
                () -> transactionsWriter.writeOther(null, new ArrayList<>()));

        assertEquals("path is null", thrown.getMessage());
    }

    @Test
    void givenValidPathAndNullList_whenWriteMatched_thenFail() throws IOException {
        Path path = Files.createTempFile("temp", ".any");
        FilePath filePath = new FilePath(path);

        TransactionsWriterException thrown = assertThrows(TransactionsWriterException.class,
                () -> transactionsWriter.writeMatched(filePath, null));

        assertEquals("list is null", thrown.getMessage());
    }

    @Test
    void givenValidPathAndNullList_whenWriteOther_thenFail() throws IOException {
        Path path = Files.createTempFile("temp", ".any");
        FilePath filePath = new FilePath(path);

        TransactionsWriterException thrown = assertThrows(TransactionsWriterException.class,
                () -> transactionsWriter.writeOther(filePath, null));

        assertEquals("list is null", thrown.getMessage());
    }

    @Test
    void givenValidPathAndList_whenWriteMatched_thenWriteCorrectly() throws IOException {
        Path path = Files.createTempFile("temp", ".json");
        FilePath filePath = new FilePath(path);
        List<Transaction> transactions = prepareTransactions();

        transactionsWriter.writeMatched(filePath, transactions);
        String result = Files.readString(path);

        String expected = prepareWriteMatchedExpected();

        assertEquals(expected, result);
    }

    @Test
    void givenValidPathAndList_whenWriteOther_thenWriteCorrectly() throws IOException {
        Path path = Files.createTempFile("temp", ".json");
        FilePath filePath = new FilePath(path);
        List<SourcedTransaction> sourcedTransactions = prepareSourcedTransactions();

        transactionsWriter.writeOther(filePath, sourcedTransactions);
        String result = Files.readString(path);

        String expected = prepareWriteOtherExpected();

        assertEquals(expected, result);
    }

    private String prepareWriteOtherExpected() {
        return "[\n" +
                "  {\n" +
                "    \"date\": \"2020-06-20\",\n" +
                "    \"amount\": \"500.00\",\n" +
                "    \"currency\": \"AED\",\n" +
                "    \"id\": \"TR-11111111111\",\n" +
                "    \"found in file\": \"SOURCE\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"date\": \"2020-01-20\",\n" +
                "    \"amount\": \"140.00\",\n" +
                "    \"currency\": \"USD\",\n" +
                "    \"id\": \"TR-11111111111\",\n" +
                "    \"found in file\": \"TARGET\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"date\": \"2020-01-12\",\n" +
                "    \"amount\": \"40.000\",\n" +
                "    \"currency\": \"JOD\",\n" +
                "    \"id\": \"TR-33333333333\",\n" +
                "    \"found in file\": \"SOURCE\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"date\": \"2020-01-22\",\n" +
                "    \"amount\": \"20.000\",\n" +
                "    \"currency\": \"JOD\",\n" +
                "    \"id\": \"TR-33333333333\",\n" +
                "    \"found in file\": \"TARGET\"\n" +
                "  }\n" +
                "]";
    }

    private String prepareWriteMatchedExpected() {
        return "[\n" +
                "  {\n" +
                "    \"date\": \"2020-01-20\",\n" +
                "    \"amount\": \"140.00\",\n" +
                "    \"currency\": \"USD\",\n" +
                "    \"id\": \"TR-47884222201\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"date\": \"2020-01-22\",\n" +
                "    \"amount\": \"20.000\",\n" +
                "    \"currency\": \"JOD\",\n" +
                "    \"id\": \"TR-47884222202\"\n" +
                "  }\n" +
                "]";
    }

    private List<SourcedTransaction> prepareSourcedTransactions() {
        return new ArrayList<>(Arrays.asList(
                new SourcedTransaction("SOURCE", new Transaction.Builder()
                        .setId("TR-11111111111")
                        .setAmount(new BigDecimal("500"))
                        .setCurrency(Currency.getInstance("AED"))
                        .setDate(LocalDate.parse("2020-06-20"))
                        .build()),
                new SourcedTransaction("TARGET", new Transaction.Builder()
                        .setId("TR-11111111111")
                        .setAmount(new BigDecimal("140"))
                        .setCurrency(Currency.getInstance("USD"))
                        .setDate(LocalDate.parse("2020-01-20"))
                        .build()),
                new SourcedTransaction("SOURCE", new Transaction.Builder()
                        .setId("TR-33333333333")
                        .setAmount(new BigDecimal("40.0000"))
                        .setCurrency(Currency.getInstance("JOD"))
                        .setDate(LocalDate.parse("2020-01-12"))
                        .build()),
                new SourcedTransaction("TARGET", new Transaction.Builder()
                        .setId("TR-33333333333")
                        .setAmount(new BigDecimal("20.0000"))
                        .setCurrency(Currency.getInstance("JOD"))
                        .setDate(LocalDate.parse("2020-01-22"))
                        .build())
        ));
    }

    private List<Transaction> prepareTransactions() {
        return new ArrayList<>(Arrays.asList(
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
        ));
    }
}