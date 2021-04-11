package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.ValidPath;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilePathChannelTest {

    @Test
    void givenNullValidPathObject_whenConstruct_thenFail() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new FilePathChannel(null));
        assertEquals("valid path object is null", npe.getMessage());
    }

    @Test
    void givenValidPathObject_whenConstruct_thenIsConstructedWithGivenValidPath() {
        ValidPath validPath = new ValidPath(Paths.get("src", "test", "resources", "valid-file-path"));

        FilePathChannel filePathChannel = new FilePathChannel(validPath);

        assertEquals(validPath, filePathChannel.getCommunicationSource());
    }
}