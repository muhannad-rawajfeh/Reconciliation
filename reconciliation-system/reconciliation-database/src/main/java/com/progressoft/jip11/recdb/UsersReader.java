package com.progressoft.jip11.recdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class UsersReader {

    public HashMap<String, String> read(Path path) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            skipHeader(bufferedReader);
            return getUsers(bufferedReader);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private HashMap<String, String> getUsers(BufferedReader bufferedReader) throws IOException {
        HashMap<String, String> accounts = new HashMap<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length != 2)
                throw new IllegalStateException("invalid number of fields found");
            accounts.put(values[0], values[1]);
        }
        return accounts;
    }

    private void skipHeader(BufferedReader bufferedReader) throws IOException {
        bufferedReader.readLine();
    }
}
