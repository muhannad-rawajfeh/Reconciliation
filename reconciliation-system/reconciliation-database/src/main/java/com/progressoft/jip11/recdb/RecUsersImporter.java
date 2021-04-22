package com.progressoft.jip11.recdb;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RecUsersImporter {

    private final DataSource dataSource;
    private final RecDbInitializer dbInitializer;

    public RecUsersImporter(DataSource dataSource, RecDbInitializer dbInitializer) {
        this.dataSource = dataSource;
        this.dbInitializer = dbInitializer;
    }

    public void importUsers(Path path) {
        dbInitializer.initialize(dataSource);
        UsersReader usersReader = new UsersReader();
        HashMap<String, String> users = usersReader.read(path);
        try (Connection connection = dataSource.getConnection()) {
            insertUsers(users, connection);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void insertUsers(HashMap<String, String> users, Connection connection) throws SQLException {
        try (PreparedStatement insertion = connection.prepareStatement("insert into rec_users (name, pass) values (?, ?)")) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                insertion.setString(1, entry.getKey());
                String hashedPassword = hashPassword(entry.getValue());
                insertion.setString(2, hashedPassword);
                insertion.addBatch();
            }
            insertion.executeBatch();
        }
    }

    private String hashPassword(String value) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length(); i++)
            builder.append((char) (7 * value.charAt(i)));
        return builder.toString();
    }
}
