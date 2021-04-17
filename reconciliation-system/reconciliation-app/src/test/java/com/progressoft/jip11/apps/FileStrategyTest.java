package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.reconciliators.SourcedTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileStrategyTest {

    private FileStrategy fileStrategy;

    @BeforeEach
    void setUp() {
        fileStrategy = new FileStrategy();
    }

    @Test
    void givenNullExportRequest_whenExportTransactions_thenFail() {
        FileStrategyException fse = assertThrows(FileStrategyException.class,
                () -> fileStrategy.exportTransactions(null));

        assertEquals("import request is null", fse.getMessage());
    }

    @Test
    void givenValidExportRequestButResultsDirectoryAlreadyExists_whenExport_thenFail() throws IOException {
        Files.createDirectory(Paths.get("reconciliation-results"));
        ExportRequest request = new ExportRequest(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        FileStrategyException fse = assertThrows(FileStrategyException.class,
                () -> fileStrategy.exportTransactions(request));

        assertEquals("error occurred while creating results directory", fse.getMessage());

        Files.delete(Paths.get("reconciliation-results"));
    }

    @Test
    void givenValidExportRequest_whenExportTransactions_thenExportToGeneratedDirectoryInRelativePathCorrectly() throws IOException {
        List<Transaction> matched = prepareMatched();
        List<SourcedTransaction> mismatched = prepareMismatched();
        List<SourcedTransaction> missing = prepareMissing();
        ExportRequest exportRequest = new ExportRequest(matched, mismatched, missing);

        fileStrategy.exportTransactions(exportRequest);

        Path dirPath = Paths.get("reconciliation-results");
        Path matchedPath = Paths.get(dirPath.toAbsolutePath().toString(), "matched.csv");
        Path mismatchedPath = Paths.get(dirPath.toAbsolutePath().toString(), "mismatched.csv");
        Path missingPath = Paths.get(dirPath.toAbsolutePath().toString(), "missing.csv");

        assertTrue(Files.isDirectory(dirPath));
        assertTrue(Files.exists(matchedPath));
        assertTrue(Files.exists(mismatchedPath));
        assertTrue(Files.exists(missingPath));

        List<String> matchedResult = Files.readAllLines(matchedPath);
        List<String> mismatchedResult = Files.readAllLines(mismatchedPath);
        List<String> missingResult = Files.readAllLines(missingPath);

        String s1 = addHeader(matched.toString(), "transaction id,amount,currency code,value date, ");
        String s2 = addHeader(mismatched.toString(), "found in file,transaction id,amount,currency code,value date, ");
        String s3 = addHeader(missing.toString(), "found in file,transaction id,amount,currency code,value date, ");

        assertEquals(s1, matchedResult.toString());
        assertEquals(s2, mismatchedResult.toString());
        assertEquals(s3, missingResult.toString());

        Files.delete(matchedPath);
        Files.delete(mismatchedPath);
        Files.delete(missingPath);
        Files.delete(dirPath);
    }

    private String addHeader(String toAdd, String header) {
        return "[" + header + toAdd.substring(1);
    }

    private List<SourcedTransaction> prepareMissing() {
        return new ArrayList<>(Arrays.asList(
                new SourcedTransaction("SOURCE", new Transaction.Builder()
                        .setId("TR-11111111111")
                        .setAmount(new BigDecimal("500"))
                        .setCurrency(Currency.getInstance("AED"))
                        .setDate(LocalDate.parse("2020-06-20"))
                        .build()),
                new SourcedTransaction("SOURCE", new Transaction.Builder()
                        .setId("TR-22222222222")
                        .setAmount(new BigDecimal("40.000"))
                        .setCurrency(Currency.getInstance("JOD"))
                        .setDate(LocalDate.parse("2020-01-12"))
                        .build()),
                new SourcedTransaction("TARGET", new Transaction.Builder()
                        .setId("TR-33333333333")
                        .setAmount(new BigDecimal("140"))
                        .setCurrency(Currency.getInstance("USD"))
                        .setDate(LocalDate.parse("2020-01-20"))
                        .build()),
                new SourcedTransaction("TARGET", new Transaction.Builder()
                        .setId("TR-44444444444")
                        .setAmount(new BigDecimal("20.0000"))
                        .setCurrency(Currency.getInstance("JOD"))
                        .setDate(LocalDate.parse("2020-01-22"))
                        .build())
        ));
    }

    private List<SourcedTransaction> prepareMismatched() {
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

    private List<Transaction> prepareMatched() {
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