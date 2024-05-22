package com.example.eksamensprojektprojektmanager.repository;

import com.example.eksamensprojektprojektmanager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TaskRepository() {
    }

    private Connection connection;

    public void addTask(Task task, Long projectId, Long subprojectId) {
        String insertQuery = "INSERT INTO tasks (project_id, subproject_id, name, description, date, deadline, status) VALUES (?, ?, ?, ?, ?, ?,?)";
        int rowsInserted = jdbcTemplate.update(insertQuery, projectId, subprojectId,
                task.getName(), task.getDescription(), task.getDate(), task.getDeadline(),task.getStatus());
    }


    public List<Task> findByProjectIdAndSubprojectId(Long projectId, Long subprojectId) {
        String query = "SELECT * FROM tasks WHERE project_id = ? AND subproject_id = ? ORDER BY status";
        return jdbcTemplate.query(query, new Object[]{projectId, subprojectId}, (resultSet, i) -> {
            Task task = new Task();
            task.setTask_id(resultSet.getLong("task_id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            task.setDate(resultSet.getTimestamp("date").toLocalDateTime());
            task.setDeadline(resultSet.getTimestamp("deadline").toLocalDateTime());
            task.setStatus(resultSet.getString("status"));
            return task;
        });
    }

    public List<Task> findByProjectIdAndSubProjectIdJPA(Long projectId, Long subprojectId) {
        String query = "SELECT * FROM tasks WHERE project_id = ? AND subproject_id = ? ORDER BY status";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, projectId.toString());
            preparedStatement.setString(2, subprojectId.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                Long taskId = resultSet.getLong("task_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                String status = resultSet.getString("status");

                tasks.add(new Task(taskId, name, description, date, deadline, status));
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


public Task findById(Long task_id) {
    String query = "SELECT * FROM tasks WHERE task_id = ?";
    return jdbcTemplate.queryForObject(query, new Object[]{task_id}, (resultSet, i) -> {
        Task task = new Task();
        task.setTask_id(resultSet.getLong("task_id"));
        task.setName(resultSet.getString("name"));
        task.setDescription(resultSet.getString("description"));
        task.setDate(resultSet.getTimestamp("date").toLocalDateTime());
        task.setDeadline(resultSet.getTimestamp("deadline").toLocalDateTime());
        task.setStatus(resultSet.getString("status")); // Set the status here
        return task;
    });
}

    public void deleteTaskById(Long task_id) {
        String query = "DELETE FROM tasks WHERE task_id = ?";
        jdbcTemplate.update(query, task_id);
    }

    public boolean deleteTaskByIdJPA(Long taskId) {
    String deleteTaskQuery = "DELETE FROM tasks WHERE task_id = ?";

    try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
         PreparedStatement deleteTaskStatement = connection.prepareStatement(deleteTaskQuery)) {

        deleteTaskStatement.setLong(1, taskId);

        int taskDeleted = deleteTaskStatement.executeUpdate();

        if (taskDeleted > 0) {
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

    public void deleteTasksBySubprojectId(Long subprojectId) {

        String query = "DELETE FROM tasks WHERE subproject_id = ?";
        jdbcTemplate.update(query, subprojectId);
    }

    public Task updateTask(Task task) {
        String updateQuery = "UPDATE tasks SET name = ?, description = ?, date = ?, deadline = ? WHERE task_id = ?";
        int rowsUpdated = jdbcTemplate.update(updateQuery, task.getName(), task.getDescription(), task.getDate(), task.getDeadline(), task.getTask_id());
        if (rowsUpdated > 0) {
            return task;
        }
        return null;
    }


    public void updateTaskStatus(Long task_id, String status) {
        String updateQuery = "UPDATE tasks SET status = ? WHERE task_id = ?";
        int rowsUpdated = jdbcTemplate.update(updateQuery, status, task_id);
        if (rowsUpdated > 0) {
            System.out.println("Task status was updated successfully!");
        }
    }


}