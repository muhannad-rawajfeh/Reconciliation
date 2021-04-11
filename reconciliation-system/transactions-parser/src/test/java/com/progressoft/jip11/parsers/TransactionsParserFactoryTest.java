package com.progressoft.jip11.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsParserFactoryTest {

    private TransactionsParserFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TransactionsParserFactory();
    }

    @Test
    void givenNullType_whenCreateParser_thenFail() {
        TransactionsParserFactoryException thrown = assertThrows(TransactionsParserFactoryException.class,
                () -> factory.createParser(null));

        assertEquals("format is null", thrown.getMessage());
    }

    @Test
    void givenUnknownType_whenCreateParser_thenFail() {
        TransactionsParserFactoryException thrown = assertThrows(TransactionsParserFactoryException.class,
                () -> factory.createParser("xml"));

        assertEquals("unknown format", thrown.getMessage());
    }

    @Test
    void givenTransactionsParserType_whenCreateParser_thenCreateRequestedType() {
        TransactionsParser parser = factory.createParser("csv");
        assertTrue(parser instanceof CSVTransactionsParser);

        TransactionsParser parser2 = factory.createParser("json");
        assertTrue(parser2 instanceof JSONTransactionsParser);
    }
}