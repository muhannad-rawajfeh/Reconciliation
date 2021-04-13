package com.progressoft.jip11.apps;

import java.io.IOException;

public class FileStrategyException extends RuntimeException {

    public FileStrategyException(String message) {
        super(message);
    }

    public FileStrategyException(String message, IOException e) {
        super(message, e);
    }
}
