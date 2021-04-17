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

class CSVFileWriterTest {

    private CSVFileWriter csvFileWriter;

    @BeforeEach
    void setUp() {
        csvFileWriter = new CSVFileWriter();
    }

    @Test
    void givenNullPath_whenWrite_thenFail() {
        CSVFileWriterException thrown = assertThrows(CSVFileWriterException.class,
                () -> csvFileWriter.write(null, new ArrayList<>(), ""));

        assertEquals("path is null", thrown.getMessage());
    }

    @Test
    void givenValidPathAndNullList_whenWrite_thenFail() throws IOException {
        Path path = Files.createTempFile("temp", "any");
        FilePath filePath = new FilePath(path);

        CSVFileWriterException thrown = assertThrows(CSVFileWriterException.class,
                () -> csvFileWriter.write(filePath, null, ""));

        assertEquals("list is null", thrown.getMessage());
    }

    @Test
    void givenValidPathAndList_whenWrite_thenWriteCorrectly() throws IOException {
        Path path = Files.createTempFile("temp" + new Random().nextInt(), ".csv");
        FilePath filePath = new FilePath(path);
        List<Transaction> transactions = prepareTransactions();

        csvFileWriter.write(filePath, transactions, "transaction id,amount,currency code,value date\n");
        List<String> result = Files.readAllLines(path);

        List<String> expected = new ArrayList<>();
        expected.add("transaction id,amount,currency code,value date");
        expected.add(transactions.get(0).toString());
        expected.add(transactions.get(1).toString());

        assertEquals(expected, result);
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