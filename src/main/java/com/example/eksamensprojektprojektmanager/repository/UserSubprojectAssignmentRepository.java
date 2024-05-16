package com.example.eksamensprojektprojektmanager.repository;

import com.example.eksamensprojektprojektmanager.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserSubprojectAssignmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserSubprojectAssignmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void assignUserToSubproject(Long userId, Long subprojectId) {
        String sql = "INSERT INTO user_subproject_assignments (user_id, subproject_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, subprojectId);

    }
    public List<Account> findAssignedUsersBySubprojectId(Long subprojectId) {
        String sql = "SELECT * FROM users WHERE user_id IN (SELECT user_id FROM user_subproject_assignments WHERE subproject_id = ?)";
        return jdbcTemplate.query(sql, new Object[]{subprojectId}, new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
                Account account = new Account();
                account.setUser_id(rs.getLong("user_id"));
                account.setUsername(rs.getString("username"));
                // Set other fields of the account object based on the result set
                return account;
            }
        });
    }
}