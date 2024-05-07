package com.example.eksamensprojektprojektmanager;

import com.example.eksamensprojektprojektmanager.model.Account;
import com.example.eksamensprojektprojektmanager.repository.AccountRepository;
import com.example.eksamensprojektprojektmanager.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;


    @Test
    public void testSave() {
        // Given
        Account account = new Account("testUser", "testPassword", true);

        // When
        accountRepository.save(account);

        // Then
        verify(jdbcTemplate).update(
                eq("INSERT INTO users (username, password, is_Admin) VALUES (?, ?, ?)"),
                any(String.class), any(String.class), any(Boolean.class)
        );
    }


    @Test
    public void testIsValidUser() {
        // Given
        String username = "testUser";
        String password = "testPassword";

        // Stubbing the jdbcTemplate's query method to return a list of integers
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(1)); // Assuming the query returns a count of 1

        // Then
        assertTrue(accountService.isValidUser(username, password));
    }


}


