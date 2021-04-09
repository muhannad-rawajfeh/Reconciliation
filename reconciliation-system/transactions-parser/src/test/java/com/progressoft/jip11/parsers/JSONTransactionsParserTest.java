package com.progressoft.jip11.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JSONTransactionsParserTest {

    private TransactionsParser transactionsParser;

    @BeforeEach
    void setUp() {
        transactionsParser = new JSONTransactionsParser();
    }

    @Test
    void givenFileWithInvalidNoOfFieldsRecord_whenParse_thenFail() {
        Path path = Paths.get("src", "test", "resources", "invalid-no-of-fields.json");

        TransactionsParserException tpe = assertThrows(TransactionsParserException.class,
                () -> transactionsParser.parse(new ValidPath(path)));

        assertEquals("invalid number of fields in line 7", tpe.getMessage());
    }
}
