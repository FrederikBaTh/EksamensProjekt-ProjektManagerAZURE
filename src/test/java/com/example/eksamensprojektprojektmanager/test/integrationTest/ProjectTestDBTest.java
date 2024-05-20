package com.example.eksamensprojektprojektmanager.test.integrationTest;

import com.example.eksamensprojektprojektmanager.testDatabase.ProjectTestDB;
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
class ProjectTestDBTest {

    @Autowired
    private ProjectTestDB projectTestDB;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        projectTestDB.createProjectTestDB();
    }

    @Test
    void testTableCreated() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE 'projectsTest'");
            Assertions.assertTrue(resultSet.next(), "Table projectsTest should exist");
        }
    }

    @Test
    void testTestDataInserted() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM projectsTest WHERE name = 'Test Project'");
            Assertions.assertTrue(resultSet.next(), "Test project should exist");
        }
    }
}