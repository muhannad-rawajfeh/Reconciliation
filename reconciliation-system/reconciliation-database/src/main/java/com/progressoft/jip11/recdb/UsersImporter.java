package com.progressoft.jip11.recdb;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UsersImporter {

    private static final String INSERT_USERS_SQL = "insert into rec_users values (?, ?, ?)";
    private final DataSource dataSource;

    public UsersImporter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void importUsers(Path path) {
        UsersReader usersReader = new UsersReader();
        List<User> users = usersReader.read(path);
        try (Connection connection = dataSource.getConnection()) {
            insertUsers(users, connection);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void insertUsers(List<User> users, Connection connection) throws SQLException {
        try (PreparedStatement insertion = connection.prepareStatement(INSERT_USERS_SQL)) {
            for (User u : users) {
                insertion.setInt(1, u.getId());
                insertion.setString(2, u.getName());
                String hashedPassword = hashPassword(u.getPassword());
                insertion.setString(3, hashedPassword);
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
