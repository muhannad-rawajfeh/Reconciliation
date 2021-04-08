package com.progressoft.jip11.parsers;

import java.time.format.DateTimeFormatter;

public class CSVFormat implements DataFormat {

    private static final int noOfFields = 7;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public int numberOfFields() {
        return noOfFields;
    }

    @Override
    public DateTimeFormatter dateFormat() {
        return formatter;
    }
}
