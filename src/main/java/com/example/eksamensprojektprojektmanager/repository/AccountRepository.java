package com.example.eksamensprojektprojektmanager.repository;

import com.example.eksamensprojektprojektmanager.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;


    @Value("${spring.datasource.url}")
    private String DATABASE_URL;
    @Value("${spring.datasource.username}")
    private String DATABASE_USERNAME;
    @Value("${spring.datasource.password}")
    private String DATABASE_PASSWORD;

    @Autowired
    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isValidUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        List<Integer> counts = jdbcTemplate.query(sql, new Object[]{username, password}, (rs, rowNum) -> rs.next() ? rs.getInt(1) : 0);
        return !counts.isEmpty() && counts.get(0) > 0;
    }

    public String getUserIdByUsername(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, String.class, username);
    }

    public void save(Account account) {
        String sql = "INSERT INTO users (username, password, is_Admin) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, account.getUsername(), account.getPassword(), account.isAdmin());
    }

}
