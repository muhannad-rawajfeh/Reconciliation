package com.progressoft.jip11.parsers;

import java.nio.file.Files;
import java.nio.file.Path;

public class ValidPath {

    private final Path path;

    public ValidPath(Path path) {
        validatePath(path);
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    private void validatePath(Path path) {
        if (path == null) {
            throw new InvalidPathException("path is null");
        }
        if (Files.notExists(path)) {
            throw new InvalidPathException("path does not exist");
        }
        if (Files.isDirectory(path)) {
            throw new InvalidPathException("path is a directory");
        }
    }
}
