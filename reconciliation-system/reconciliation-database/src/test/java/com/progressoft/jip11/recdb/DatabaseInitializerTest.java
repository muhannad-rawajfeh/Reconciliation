package com.progressoft.jip11.recdb;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseInitializerTest {

    private DatabaseInitializer initializer;

    @BeforeEach
    void setUp() {
        initializer = new DatabaseInitializer();
    }

    @Test
    void givenNullDataSource_whenInitialize_thenFail() {
        NullPointerException npe = assertThrows(NullPointerException.class,
                () -> initializer.initialize(null));
        assertEquals("datasource is null", npe.getMessage());
    }

    @Test
    void givenValidDataSource_whenInitialize_thenSucceed() throws IOException, SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        Path rec_db = Files.createTempFile("rec_db", ".db");
        dataSource.setUrl("jdbc:h2:file:" + rec_db);
        dataSource.setUser("");
        dataSource.setPassword("");

        initializer.initialize(dataSource);

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet tables = metaData.getTables(null, null, null, null)) {
                String tableName = "";
                while (tables.next()) {
                    tableName = tables.getString(3);
                    if (tableName.equalsIgnoreCase("rec_users")) break;
                }
                assertTrue(tableName.equalsIgnoreCase("rec_users"));
            }
            insertIntoTable(connection);
            assertEntries(connection);
        }
    }

    private void assertEntries(Connection connection) throws SQLException {
        try (PreparedStatement query = connection.prepareStatement("select * from rec_users")) {
            try (ResultSet resultSet = query.executeQuery()) {
                assertTrue(resultSet.next());
                assertEquals(1, resultSet.getInt(1));
                assertEquals("ali", resultSet.getString(2));
                assertEquals("pass", resultSet.getString(3));
                assertTrue(resultSet.next());
                assertEquals(2, resultSet.getInt(1));
                assertEquals("moh", resultSet.getString(2));
                assertEquals("pass", resultSet.getString(3));
                assertFalse(resultSet.next());
            }
        }
    }

    private void insertIntoTable(Connection connection) throws SQLException {
        try (PreparedStatement insertion = connection.prepareStatement("insert into rec_users values (?, ?, ?), (?, ?, ?)")) {
            insertion.setInt(1, 1);
            insertion.setString(2, "ali");
            insertion.setString(3, "pass");
            insertion.setInt(4, 2);
            insertion.setString(5, "moh");
            insertion.setString(6, "pass");
            int affected = insertion.executeUpdate();
            assertEquals(2, affected);
        }
    }
}