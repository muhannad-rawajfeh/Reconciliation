package com.progressoft.jip11.parsers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsParserFactoryTest {

    @Test
    void givenNullType_whenCreateParser_thenFail() {
        TransactionsParserFactoryException thrown = assertThrows(TransactionsParserFactoryException.class,
                () -> TransactionsParserFactory.createParser(null));

        assertEquals("format is null", thrown.getMessage());
    }

    @Test
    void givenUnknownType_whenCreateParser_thenFail() {
        TransactionsParserFactoryException thrown = assertThrows(TransactionsParserFactoryException.class,
                () -> TransactionsParserFactory.createParser("something"));

        assertEquals("invalid format or not yet supported", thrown.getMessage());
    }

    @Test
    void givenValidFormat_whenCreateParser_thenCreateParserOfRequestedFormat() {
        TransactionsParser parser = TransactionsParserFactory.createParser("csv");
        assertTrue(parser instanceof CSVTransactionsParser);

        TransactionsParser parser2 = TransactionsParserFactory.createParser("json");
        assertTrue(parser2 instanceof JSONTransactionsParser);
    }
}