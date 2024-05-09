package com.example.eksamensprojektprojektmanager.repository;

import com.example.eksamensprojektprojektmanager.model.Subproject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubprojectRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskRepository taskRepository;

    public SubprojectRepository() {
    }

    public void addSubproject(Subproject subproject, Long projectId) {
        String insertQuery = "INSERT INTO subprojects (project_id, name, description, startDate, deadline) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertQuery, projectId,
                subproject.getSubprojectname(), subproject.getDescription(), subproject.getStartDate(), subproject.getDeadline());
    }

    public List<Subproject> findByProjectId(Long projectId) {
        String query = "SELECT * FROM subprojects WHERE project_id = ?";
        return jdbcTemplate.query(query, new Object[]{projectId}, (resultSet, i) -> {
            Subproject subproject = new Subproject();
            subproject.setSubproject_id(resultSet.getLong("subproject_id"));
            subproject.setSubprojectname(resultSet.getString("name"));
            subproject.setDescription(resultSet.getString("description"));
            subproject.setStartDate(resultSet.getDate("startDate").toLocalDate());
            subproject.setDeadline(resultSet.getDate("deadline").toLocalDate());
            return subproject;
        });
    }

    public void deleteSubprojectById(Long subprojectId) {
        String query = "DELETE FROM subprojects WHERE subproject_id = ?";
        jdbcTemplate.update(query, subprojectId);
    }


    public void deleteSubprojectsByProjectId(Long projectId) {
        List<Subproject> subprojects = getSubprojectsByProjectId(projectId);

        for (Subproject subproject : subprojects) {
            taskRepository.deleteTasksBySubprojectId(subproject.getSubproject_id());

            String query = "DELETE FROM subprojects WHERE subproject_id = ?";
            jdbcTemplate.update(query, subproject.getSubproject_id());
        }
    }

    public Subproject findById(Long subprojectId) {
        String query = "SELECT * FROM subprojects WHERE subproject_id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{subprojectId}, (resultSet, i) -> {
            Subproject subproject = new Subproject();
            subproject.setSubprojectname(resultSet.getString("name"));
            subproject.setDescription(resultSet.getString("description"));
            subproject.setStartDate(resultSet.getDate("startDate").toLocalDate());
            subproject.setDeadline(resultSet.getDate("deadline").toLocalDate());
            return subproject;
        });
    }

    public List<Subproject> getSubprojectsByProjectId(Long projectId) {
        String query = "SELECT * FROM subprojects WHERE project_id = ?";
        return jdbcTemplate.query(query, new Object[]{projectId}, (resultSet, i) -> {
            Subproject subproject = new Subproject();
            subproject.setSubproject_id(resultSet.getLong("subproject_id"));
            subproject.setSubprojectname(resultSet.getString("name"));
            subproject.setDescription(resultSet.getString("description"));
            subproject.setStartDate(resultSet.getDate("startDate").toLocalDate());
            subproject.setDeadline(resultSet.getDate("deadline").toLocalDate());
            return subproject;
        });
    }

    public Subproject updateSubproject(Subproject subproject) {
        String updateQuery = "UPDATE subprojects SET name = ?, description = ?, startDate = ?, deadline = ? WHERE subproject_id = ?";
        int rowsUpdated = jdbcTemplate.update(updateQuery, subproject.getSubprojectname(),
                subproject.getDescription(),
                java.sql.Date.valueOf(subproject.getStartDate()),
                java.sql.Date.valueOf(subproject.getDeadline()),
                subproject.getSubproject_id());
        if (rowsUpdated > 0) {
            System.out.println("Subproject was updated successfully!");
            return subproject;
        }
        return null;
    }

}