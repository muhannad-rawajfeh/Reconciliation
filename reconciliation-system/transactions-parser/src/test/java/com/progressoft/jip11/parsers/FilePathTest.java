package com.progressoft.jip11.parsers;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilePathTest {

    @Test
    void givenNullPath_whenConstruct_thenFail() {
        InvalidFilePathException ipe = assertThrows(InvalidFilePathException.class, () -> new FilePath(null));

        assertEquals("path is null", ipe.getMessage());
    }

    @Test
    void givenNoneExistingPath_whenConstruct_thenFail() {
        Path path = Paths.get("foo" + new Random().nextInt());

        InvalidFilePathException ipe = assertThrows(InvalidFilePathException.class, () -> new FilePath(path));

        assertEquals("path does not exist", ipe.getMessage());
    }

    @Test
    void givenDirectoryPath_whenConstruct_thenFail() throws IOException {
        Path path = Files.createTempDirectory("temp");

        InvalidFilePathException ipe = assertThrows(InvalidFilePathException.class, () -> new FilePath(path));

        assertEquals("path is a directory", ipe.getMessage());
    }

    @Test
    void givenValidFilePath_whenConstruct_thenSucceed() throws IOException {
        Path path = Files.createTempFile("temp", ".any");

        FilePath filePath = new FilePath(path);

        assertEquals(path, filePath.getPath());
    }
}