package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.ValidPath;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilePathChannelTest {

    @Test
    void givenNullValidPathObject_whenConstruct_thenFail() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new FilePathChannel(null));

        assertEquals("valid path object is null", npe.getMessage());
    }

    @Test
    void givenValidPathObject_whenConstruct_thenIsConstructedWithGivenValidPath() throws IOException {
        Path path = Files.createTempFile("temp", "any");
        ValidPath validPath = new ValidPath(path);

        FilePathChannel filePathChannel = new FilePathChannel(validPath);

        assertEquals(validPath, filePathChannel.getCommunicationSource());
    }
}