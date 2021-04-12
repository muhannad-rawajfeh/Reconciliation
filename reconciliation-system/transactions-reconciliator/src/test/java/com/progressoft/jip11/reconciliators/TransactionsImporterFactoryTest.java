package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.ValidPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsImporterFactoryTest {

    private TransactionsImporterFactory transactionsImporterFactory;

    @BeforeEach
    void setUp() {
        transactionsImporterFactory = new TransactionsImporterFactory();
    }

    @Test
    void givenNullChannel_whenCreateImporter_thenFail() {
        TransactionsImporterFactoryException thrown = assertThrows(TransactionsImporterFactoryException.class,
                () -> transactionsImporterFactory.createImporter(null));

        assertEquals("channel is null", thrown.getMessage());
    }

    @Test
    void givenUnknownChannel_whenCreateImporter_thenFail() {
        Channel channel = Object::new;

        TransactionsImporterFactoryException thrown = assertThrows(TransactionsImporterFactoryException.class,
                () -> transactionsImporterFactory.createImporter(channel));

        assertEquals("unknown communication channel", thrown.getMessage());
    }

    @Test
    void givenValidChannel_whenCreateImporter_thenCreateImporterOfRequestedChannelType() throws IOException {
        Path path = Files.createTempFile("temp" + new Random().nextInt(), "any");
        Channel channel = new FilePathChannel(new ValidPath(path));

        TransactionsImporter importer = transactionsImporterFactory.createImporter(channel);

        assertTrue(importer instanceof CSVTransactionsImporter);
    }
}