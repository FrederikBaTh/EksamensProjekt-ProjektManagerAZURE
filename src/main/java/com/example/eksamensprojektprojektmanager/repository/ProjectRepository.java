package com.example.eksamensprojektprojektmanager.repository;

import com.example.eksamensprojektprojektmanager.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ProjectRepository {


    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ProjectRepository() {
    }

    public Project addProject(Project project) {
        String insertQuery = "INSERT INTO Projects (ProjectName, StartDate, ProjectDeadline, ProjectStatus) VALUES (?, ?, ?, ?)";
        int rowsInserted = jdbcTemplate.update(insertQuery, project.getProjectName(),
                java.sql.Date.valueOf(project.getStartDate()),
                java.sql.Date.valueOf(project.getProjectDeadline()),
                project.getProjectStatus());
        if (rowsInserted > 0) {
            System.out.println("A new project was added successfully!");
            return project;
        }
        return null;
    }


    public boolean existsByNameAndUserId(String name, int userId) {
        String query = "SELECT COUNT(*) FROM Projects WHERE ProjectName = ? AND UserID = ?";
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{name, userId}, Integer.class);
        return count != null && count > 0;
    }


    public List<Project> findByUserId(int userId) {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM Projects WHERE UserID = ?";
        try (Connection connection = DriverManager.getConnection(db_url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Project project = new Project();
                    project.setProject_id(resultSet.getLong("Project_id")); // Set projectId as int
                    project.setProjectName(resultSet.getString("ProjectName"));
                    projects.add(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }



}