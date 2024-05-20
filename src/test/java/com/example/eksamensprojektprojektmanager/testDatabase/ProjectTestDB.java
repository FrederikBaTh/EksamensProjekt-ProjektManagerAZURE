package com.example.eksamensprojektprojektmanager.testDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@Profile("test")
public class ProjectTestDB {

    private final DataSource dataSource;

    @Autowired
    public ProjectTestDB(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createProjectTestDB() {

        String createTableSQL = "CREATE TABLE projectsTest (" +
                "project_id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "user_id BIGINT," +
                "name VARCHAR(100) NOT NULL," +
                "description TEXT," +
                "startDate DATE," +
                "deadline DATE," +
                "FOREIGN KEY (user_id) REFERENCES usersTest(user_id)" +
                ");";

        // SQL for inserting test data
        String insertTestDataSQL = "INSERT INTO projectsTest (user_id, name, description, startDate, deadline) " +
                "VALUES (1, 'Test Project', 'This is a test project', '2024-06-01', '2024-6-6');";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false);

            statement.addBatch("SET foreign_key_checks = 0;");
            statement.addBatch("DROP TABLE IF EXISTS projectsTest;");
            statement.addBatch(createTableSQL);
            statement.addBatch(insertTestDataSQL);  // Add this line
            statement.addBatch("SET foreign_key_checks = 1;");

            statement.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating test database", e);
        }
    }
}