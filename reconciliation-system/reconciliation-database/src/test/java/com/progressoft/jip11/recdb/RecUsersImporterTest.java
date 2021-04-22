package com.progressoft.jip11.recdb;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RecUsersImporterTest {

    @Test
    void givenUsersFilePath_whenImportUsers_thenImportCorrectlyWithPasswordsHashed() throws IOException, SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        Path rec_db = Files.createTempFile("rec_db", ".db");
        dataSource.setUrl("jdbc:h2:file:" + rec_db);
        dataSource.setUser("");
        dataSource.setPassword("");

        RecDbInitializer initializer = new RecDbInitializer();
        initializer.initialize(dataSource);

        RecUsersImporter usersImporter = new RecUsersImporter(dataSource);
        Path path = Paths.get("src", "test", "resources", "users.csv");
        usersImporter.importUsers(path);

        try (Connection connection = dataSource.getConnection()) {
            assertUsers(connection);
        }
    }

    private void assertUsers(Connection connection) throws SQLException {
        try (PreparedStatement query = connection.prepareStatement("select * from rec_users")) {
            try (ResultSet resultSet = query.executeQuery()) {
                resultSet.next();
                assertEquals(1, resultSet.getInt(1));
                assertEquals("mohammad", resultSet.getString(2));
                assertEquals(hashPassword("ab1234"), resultSet.getString(3));
                resultSet.next();
                assertEquals(2, resultSet.getInt(1));
                assertEquals("ali", resultSet.getString(2));
                assertEquals(hashPassword("123456"), resultSet.getString(3));
                assertFalse(resultSet.next());
            }
        }
    }

    private String hashPassword(String value) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length(); i++)
            builder.append((char) (7 * value.charAt(i)));
        return builder.toString();
    }
}