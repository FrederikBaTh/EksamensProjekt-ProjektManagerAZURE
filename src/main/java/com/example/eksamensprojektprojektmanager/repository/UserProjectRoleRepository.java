package com.example.eksamensprojektprojektmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserProjectRoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getRoleByUserIdAndProjectId(Long userId, Long projectId) {
        String sql = "SELECT role FROM user_project_roles WHERE user_id = ? AND project_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, projectId}, String.class);
    }


}
