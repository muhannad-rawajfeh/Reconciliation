package com.progressoft.jip11.parsers;

import java.nio.file.Files;
import java.nio.file.Path;

public class FilePath {

    private final Path path;

    public FilePath(Path path) {
        validatePath(path);
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    private void validatePath(Path path) {
        if (path == null)
            throw new MyInvalidPathException("path is null");
        if (Files.notExists(path))
            throw new MyInvalidPathException("path does not exist");
        if (Files.isDirectory(path))
            throw new MyInvalidPathException("path is a directory");
    }
}
