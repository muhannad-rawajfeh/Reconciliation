package com.progressoft.jip11.recdb;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsersReaderTest {

    @Test
    void givenUsersFile_whenRead_thenReadCorrectly() {
        UsersReader usersReader = new UsersReader();
        Path path = Paths.get("src", "test", "resources", "users.csv");
        List<User> users = usersReader.read(path);
        User user1 = users.get(0);
        assertEquals(1, user1.getId());
        assertEquals("ali", user1.getName());
        assertEquals("123456", user1.getPassword());
        User user2 = users.get(1);
        assertEquals(2, user2.getId());
        assertEquals("mohammad", user2.getName());
        assertEquals("ab1234", user2.getPassword());
    }
}