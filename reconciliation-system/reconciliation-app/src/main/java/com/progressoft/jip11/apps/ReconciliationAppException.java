package com.progressoft.jip11.apps;

import java.io.IOException;

public class ReconciliationAppException extends RuntimeException {

    public ReconciliationAppException(String message, IOException e) {
        super(message, e);
    }
}
