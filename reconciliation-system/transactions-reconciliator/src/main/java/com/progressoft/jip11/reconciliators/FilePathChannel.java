package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.ValidPath;

public class FilePathChannel implements Channel {

    private final ValidPath validPath;

    public FilePathChannel(ValidPath validPath) {
        validate(validPath);
        this.validPath = validPath;
    }

    @Override
    public Object getCommunicationSource() {
        return validPath;
    }

    @Override
    public String toString() {
        return validPath.getPath().toAbsolutePath().toString();
    }

    private void validate(ValidPath validPath) {
        if (validPath == null)
            throw new NullPointerException("valid path object is null");
    }
}
