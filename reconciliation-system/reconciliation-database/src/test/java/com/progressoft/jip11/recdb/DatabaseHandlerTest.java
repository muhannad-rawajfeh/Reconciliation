package com.progressoft.jip11.recdb;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseHandlerTest {

    @Test
    void givenUserNameAndPassword_whenIsValidLoginRequest_thenReturnIfIsValid() throws IOException {
        JdbcDataSource dataSource = new JdbcDataSource();
        Path rec_db = Files.createTempFile("rec_db", ".db");
        dataSource.setUrl("jdbc:h2:file:" + rec_db);
        dataSource.setUser("");
        dataSource.setPassword("");

        DatabaseInitializer initializer = new DatabaseInitializer();
        initializer.initialize(dataSource);

        UsersImporter usersImporter = new UsersImporter(dataSource);
        usersImporter.importUsers(Paths.get("src", "test", "resources", "users.csv"));

        DatabaseHandler databaseHandler = new DatabaseHandler(dataSource);
        assertTrue(databaseHandler.isValidLoginRequest("ali", "123456"));
        assertFalse(databaseHandler.isValidLoginRequest("ali", "123"));
        assertFalse(databaseHandler.isValidLoginRequest("a", "123456"));
    }
}