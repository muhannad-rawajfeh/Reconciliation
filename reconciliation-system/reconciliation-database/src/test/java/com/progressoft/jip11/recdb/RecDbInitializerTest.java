package com.progressoft.jip11.recdb;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RecDbInitializerTest {

    private RecDbInitializer initializer;

    @BeforeEach
    void setUp() {
        initializer = new RecDbInitializer();
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
            insertIntoTable(connection);
            checkStructureAndIdIncrement(connection);
        }
    }

    private void checkStructureAndIdIncrement(Connection connection) throws SQLException {
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
        try (PreparedStatement insertion = connection.prepareStatement("insert into rec_users (name, pass) values (?, ?), (?, ?)")) {
            insertion.setString(1, "ali");
            insertion.setString(2, "pass");
            insertion.setString(3, "moh");
            insertion.setString(4, "pass");
            int affected = insertion.executeUpdate();
            assertEquals(2, affected);
        }
    }
}