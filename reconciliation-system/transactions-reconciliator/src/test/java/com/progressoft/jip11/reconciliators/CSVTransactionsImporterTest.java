package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.ValidPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVTransactionsImporterTest {

    private TransactionsImporter transactionsImporter;

    @BeforeEach
    void setUp() {
        transactionsImporter = new CSVTransactionsImporter();
    }

    @Test
    void givenNullChannel_whenImport_thenFail() {
        CSVTransactionsImporterException thrown = assertThrows(CSVTransactionsImporterException.class,
                () -> transactionsImporter.importMatchingTransactions(null, new ArrayList<>()));

        assertEquals("channel is null", thrown.getMessage());
    }

    @Test
    void givenNotFilePathChannelInstance_whenImport_thenFail() {
        Channel channel = Object::new;

        CSVTransactionsImporterException thrown = assertThrows(CSVTransactionsImporterException.class,
                () -> transactionsImporter.importMatchingTransactions(channel, new ArrayList<>()));

        assertEquals("invalid communication channel", thrown.getMessage());
    }

    @Test
    void givenValidChannelAndNullList_whenImport_thenFail() {
        Channel channel = new FilePathChannel(new ValidPath(Paths.get("src", "test", "resources", "valid-file-path")));

        CSVTransactionsImporterException thrown = assertThrows(CSVTransactionsImporterException.class,
                () -> transactionsImporter.importMatchingTransactions(channel, null));

        assertEquals("list is null", thrown.getMessage());
    }
}