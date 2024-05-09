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

    public void addTask(Task task, Long projectId, Long subprojectId) {
        String insertQuery = "INSERT INTO tasks (project_id, subproject_id, name, description, date, deadline) VALUES (?, ?, ?, ?, ?, ?)";
        int rowsInserted = jdbcTemplate.update(insertQuery, projectId, subprojectId,
                task.getName(), task.getDescription(), task.getDate(), task.getDeadline());
    }


    public List<Task> findByProjectIdAndSubprojectId(Long projectId, Long subprojectId) {
        String query = "SELECT * FROM tasks WHERE project_id = ? AND subproject_id = ?";
        return jdbcTemplate.query(query, new Object[]{projectId, subprojectId}, (resultSet, i) -> {
            Task task = new Task();
            task.setTask_id(resultSet.getLong("task_id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            task.setDate(resultSet.getTimestamp("date").toLocalDateTime());
            task.setDeadline(resultSet.getTimestamp("deadline").toLocalDateTime());
            return task;
        });
    }

    public void deleteTaskById(Long task_id) {
        String query = "DELETE FROM tasks WHERE task_id = ?";
        jdbcTemplate.update(query, task_id);
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
            return task;
        });
    }


}