package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.parsers.ValidPath;
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

class CSVTransactionsImporterTest {

    private TransactionsFileImporter transactionsFileImporter;

    @BeforeEach
    void setUp() {
        transactionsFileImporter = new CSVTransactionsImporter();
    }

    @Test
    void givenNullPath_whenImportMatching_thenFail() {
        TransactionsImporterException thrown = assertThrows(TransactionsImporterException.class,
                () -> transactionsFileImporter.importMatchingTransactions(null, new ArrayList<>()));

        assertEquals("path is null", thrown.getMessage());
    }

    @Test
    void givenValidPathAndNullList_whenImportMatching_thenFail() throws IOException {
        Path path = Files.createTempFile("temp", "any");
        ValidPath validPath = new ValidPath(path);

        TransactionsImporterException thrown = assertThrows(TransactionsImporterException.class,
                () -> transactionsFileImporter.importMatchingTransactions(validPath, null));

        assertEquals("list is null", thrown.getMessage());
    }

    @Test
    void givenNullPath_whenImportOther_thenFail() {
        TransactionsImporterException thrown = assertThrows(TransactionsImporterException.class,
                () -> transactionsFileImporter.importOtherTransactions(null, new ArrayList<>()));

        assertEquals("path is null", thrown.getMessage());
    }

    @Test
    void givenValidPathAndNullList_whenImportOther_thenFail() throws IOException {
        Path path = Files.createTempFile("temp" + new Random().nextInt(), ".csv");
        ValidPath validPath = new ValidPath(path);

        TransactionsImporterException thrown = assertThrows(TransactionsImporterException.class,
                () -> transactionsFileImporter.importOtherTransactions(validPath, null));

        assertEquals("list is null", thrown.getMessage());
    }

    @Test
    void givenValidChannelAndList_whenImportMatchingTransactions_thenImportCorrectly() throws IOException {
        Path path = Files.createTempFile("temp" + new Random().nextInt(), ".csv");
        ValidPath validPath = new ValidPath(path);
        List<Transaction> transactions = prepareTransactions();

        transactionsFileImporter.importMatchingTransactions(validPath, transactions);
        List<String> result = Files.readAllLines(path);

        List<String> expected = new ArrayList<>();
        expected.add("transaction id,amount,currency code,value date");
        expected.add(transactions.get(0).toString());
        expected.add(transactions.get(1).toString());

        assertEquals(expected, result);
    }

    @Test
    void givenValidChannelAndList_whenImportOtherTransactions_thenImportCorrectly() throws IOException {
        Path path = Files.createTempFile("temp" + new Random().nextInt(), ".csv");
        ValidPath validPath = new ValidPath(path);
        List<SourcedTransaction> sourcedTransactions = prepareSourcedTransactions();

        transactionsFileImporter.importOtherTransactions(validPath, sourcedTransactions);
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