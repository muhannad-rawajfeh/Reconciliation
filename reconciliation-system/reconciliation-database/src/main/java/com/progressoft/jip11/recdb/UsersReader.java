package com.progressoft.jip11.recdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UsersReader {

    public List<User> read(Path path) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            skipHeader(bufferedReader);
            return getUsers(bufferedReader);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private List<User> getUsers(BufferedReader bufferedReader) throws IOException {
        List<User> users = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(",");
            validateNoOfFields(values);
            User user = new User(Integer.parseInt(values[0]), values[1], values[2]);
            users.add(user);
        }
        return users;
    }

    private void validateNoOfFields(String[] values) {
        if (values.length != 3)
            throw new IllegalArgumentException("invalid number of fields found");
    }

    private void skipHeader(BufferedReader bufferedReader) throws IOException {
        bufferedReader.readLine();
    }
}
