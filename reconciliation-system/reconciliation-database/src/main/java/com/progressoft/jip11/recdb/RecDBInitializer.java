package com.progressoft.jip11.recdb;

import javax.sql.DataSource;

public class RecDBInitializer {
    public void initialize(DataSource dataSource) {
        if (dataSource == null)
            throw new NullPointerException("datasource is null");
    }
}
