package com.progressoft.jip11.recdb;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UsersReaderTest {

    @Test
    void givenUsersFile_whenRead_thenReadCorrectly() {
        UsersReader usersReader = new UsersReader();
        Path path = Paths.get("src", "test", "resources", "users.csv");
        HashMap<String, String> users = usersReader.read(path);
        assertTrue(users.containsKey("ali"));
        assertEquals("123456" ,users.get("ali"));
        assertTrue(users.containsKey("mohammad"));
        assertEquals("ab1234" ,users.get("mohammad"));
    }
}