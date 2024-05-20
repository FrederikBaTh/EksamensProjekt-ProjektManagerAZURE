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
        String insertQuery = "INSERT INTO tasks (project_id, subproject_id, name, description, date, deadline, status) VALUES (?, ?, ?, ?, ?, ?,?)";
        int rowsInserted = jdbcTemplate.update(insertQuery, projectId, subprojectId,
                task.getName(), task.getDescription(), task.getDate(), task.getDeadline(),task.getStatus());
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


    public List<Task> findByStatus(String status) {
        String query = "SELECT * FROM tasks WHERE status = ?";
        List<Task> tasks = jdbcTemplate.query(query, new Object[]{status}, (resultSet, i) -> {
            Task task = new Task(
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getTimestamp("date").toLocalDateTime(),
                    resultSet.getTimestamp("deadline").toLocalDateTime(),
                    resultSet.getLong("project_id"),
                    resultSet.getLong("subproject_id"),
                    resultSet.getString("status")
            );
            return task;
        });
        System.out.println("Tasks fetched for status " + status + ": " + tasks.size()); // Log the number of tasks fetched
        return tasks;
    }

}