package com.progressoft.jip11.parsers;

import java.time.format.DateTimeFormatter;

public interface DataFormat {

    static String idRegex() {
        return "TR-\\d{11}";
    }

    int numberOfFields();

    DateTimeFormatter dateFormat();
}
