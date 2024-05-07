package com.example.eksamensprojektprojektmanager.repository;

import com.example.eksamensprojektprojektmanager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public void addTask(Task task, Integer projectId) {
        String insertQuery = "INSERT INTO tasks (project_id, name, description, date, deadline) VALUES (?, ?, ?, ?, ?)";
        int rowsInserted = jdbcTemplate.update(insertQuery, projectId,
                task.getName(), task.getDescription(), task.getDate(), task.getDeadline());
        if (rowsInserted > 0) {
            System.out.println("A new task was added successfully!");
        }
    }


    public List<Task> findByProjectId(Long projectId) {
        String query = "SELECT * FROM tasks WHERE project_id = ?";
        return jdbcTemplate.query(query, new Object[]{projectId}, (resultSet, i) -> {
            Task task = new Task();
            task.setTask_id(resultSet.getLong("task_id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            task.setDate(resultSet.getTimestamp("date").toLocalDateTime());
            task.setDeadline(resultSet.getTimestamp("deadline").toLocalDateTime());
            return task;
        });
    }

    public void deleteTasks(int projectId) {
        String query = "DELETE FROM tasks WHERE project_id = ?";
        jdbcTemplate.update(query, projectId);
    }

}