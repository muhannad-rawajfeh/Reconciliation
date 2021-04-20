package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.CSVTransactionsParser;
import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.parsers.JSONTransactionsParser;
import com.progressoft.jip11.parsers.TransactionsParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReconciliationSystemTest {

    private static final String resourcesPath = Paths.get("src", "test", "resources").toString();

    private static final TransactionsParser csvParser = new CSVTransactionsParser();
    private static final TransactionsParser jsonParser = new JSONTransactionsParser();
    private static final MockTransactionsExporter mockStrategy = new MockTransactionsExporter();
    private static final ReconciliationSystem reconciliationSystem = new ReconciliationSystem(csvParser, jsonParser, mockStrategy);

    @Test
    void givenSourceAndTargetPaths_whenReconcile_thenReconcileAndExportCorrectly() throws IOException {
        FilePath sourcePath = new FilePath(Paths.get(resourcesPath, "input-files", "transactions.csv"));
        FilePath targetPath = new FilePath(Paths.get(resourcesPath, "input-files", "transactions.json"));

        reconciliationSystem.reconcile(sourcePath, targetPath);

        List<String> expectedMatched = Files.readAllLines(Paths.get(resourcesPath, "result-files", "matched.csv"));
        List<String> expectedMismatched = Files.readAllLines(Paths.get(resourcesPath, "result-files", "mismatched.csv"));
        List<String> expectedMissing = Files.readAllLines(Paths.get(resourcesPath, "result-files", "missing.csv"));

        String s1 = addHeader(mockStrategy.getLists().get(0), "transaction id,amount,currency code,value date, ");
        String s2 = addHeader(mockStrategy.getLists().get(1), "found in file,transaction id,amount,currency code,value date, ");
        String s3 = addHeader(mockStrategy.getLists().get(2), "found in file,transaction id,amount,currency code,value date, ");

        assertEquals(expectedMatched.toString(), s1);
        assertEquals(expectedMismatched.toString(), s2);
        assertEquals(expectedMissing.toString(), s3);
    }

    private String addHeader(String toAdd, String header) {
        return "[" + header + toAdd.substring(1);
    }
}