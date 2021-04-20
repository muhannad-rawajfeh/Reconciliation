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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVTransactionsWriterTest {

    private TransactionsWriter transactionsWriter;

    @BeforeEach
    void setUp() {
        transactionsWriter = new CSVTransactionsWriter();
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
        Path path = Files.createTempFile("temp", "any");
        FilePath filePath = new FilePath(path);

        TransactionsWriterException thrown = assertThrows(TransactionsWriterException.class,
                () -> transactionsWriter.writeMatched(filePath, null));

        assertEquals("list is null", thrown.getMessage());
    }

    @Test
    void givenValidPathAndNullList_whenWriteOther_thenFail() throws IOException {
        Path path = Files.createTempFile("temp", "any");
        FilePath filePath = new FilePath(path);

        TransactionsWriterException thrown = assertThrows(TransactionsWriterException.class,
                () -> transactionsWriter.writeOther(filePath, null));

        assertEquals("list is null", thrown.getMessage());
    }

    @Test
    void givenValidPathAndList_whenWriteMatched_thenWriteCorrectly() throws IOException {
        Path path = Files.createTempFile("temp" + new Random().nextInt(), ".csv");
        FilePath filePath = new FilePath(path);
        List<Transaction> transactions = prepareTransactions();

        transactionsWriter.writeMatched(filePath, transactions);
        List<String> result = Files.readAllLines(path);

        List<String> expected = new ArrayList<>();
        expected.add("transaction id,amount,currency code,value date");
        expected.add(transactions.get(0).toString());
        expected.add(transactions.get(1).toString());

        assertEquals(expected, result);
    }

    @Test
    void givenValidPathAndList_whenWriteOther_thenWriteCorrectly() throws IOException {
        Path path = Files.createTempFile("temp" + new Random().nextInt(), ".csv");
        FilePath filePath = new FilePath(path);
        List<SourcedTransaction> sourcedTransactions = prepareSourcedTransactions();

        transactionsWriter.writeOther(filePath, sourcedTransactions);
        List<String> result = Files.readAllLines(path);

        List<String> expected = new ArrayList<>();
        expected.add("found in file,transaction id,amount,currency code,value date");
        expected.add(sourcedTransactions.get(0).toString());
        expected.add(sourcedTransactions.get(1).toString());
        expected.add(sourcedTransactions.get(2).toString());
        expected.add(sourcedTransactions.get(3).toString());

        assertEquals(expected, result);
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