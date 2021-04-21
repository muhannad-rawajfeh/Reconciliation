package com.progressoft.jip11.recdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecDBInitializerTest {

    private RecDBInitializer initializer;

    @BeforeEach
    void setUp() {
        initializer = new RecDBInitializer();
    }

    @Test
    void givenNullDataSource_whenInitialize_thenFail() {
        NullPointerException npe = assertThrows(NullPointerException.class,
                () -> initializer.initialize(null));
        assertEquals("datasource is null", npe.getMessage());
    }

    @Test
    void givenValidDataSource_whenInitialize_thenSucceed() {
    }
}