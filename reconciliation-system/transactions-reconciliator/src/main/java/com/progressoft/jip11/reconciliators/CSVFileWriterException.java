package com.progressoft.jip11.reconciliators;

import java.io.IOException;

public class CSVFileWriterException extends RuntimeException {

    public CSVFileWriterException(String message) {
        super(message);
    }

    public CSVFileWriterException(String message, IOException e) {
        super(message, e);
    }
}
