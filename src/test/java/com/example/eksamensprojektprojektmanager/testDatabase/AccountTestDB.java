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
public class AccountTestDB {

    private final DataSource dataSource;

    @Autowired
    public AccountTestDB(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createAccountTestDB() {

        String createTableSQL = "CREATE TABLE usersTest (" +
                "user_id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) NOT NULL UNIQUE," +
                "password VARCHAR(100) NOT NULL," +
                "is_admin BOOLEAN NOT NULL," +
                "name VARCHAR(100)," +
                "company VARCHAR(100)," +
                "job_title VARCHAR(100)," +
                "description TEXT" +
                ");";

        // SQL for inserting test data
        String insertTestDataSQL = "INSERT INTO usersTest (username, password, is_admin, name, company, job_title, description) " +
                "VALUES ('testUser', 'testPassword', false, 'Test Name', 'Test Company', 'Test Job Title', 'Test Description');";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false);

            statement.addBatch("SET foreign_key_checks = 0;");
            statement.addBatch("DROP TABLE IF EXISTS usersTest;");
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