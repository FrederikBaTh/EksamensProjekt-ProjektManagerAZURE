package com.example.eksamensprojektprojektmanager.test;

import com.example.eksamensprojektprojektmanager.testDatabase.AccountTestDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
@ActiveProfiles("test")
class AccountTestDBTest {

    @Autowired
    private AccountTestDB accountTestDB;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        accountTestDB.createAccountTestDB();
    }

    @Test
    void testTableCreated() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE 'usersTest'");
            Assertions.assertTrue(resultSet.next(), "Table usersTest should exist");
        }
    }

    @Test
    void testTestDataInserted() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM usersTest WHERE username = 'testUser'");
            Assertions.assertTrue(resultSet.next(), "Test user should exist");
        }
    }
}