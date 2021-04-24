package com.progressoft.jip11.recdb;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHandlerTest {

    @Test
    void givenExistingUserNameAndValidPassword_whenIsValidLoginRequest_thenReturnTrue() throws IOException {
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
    }
}