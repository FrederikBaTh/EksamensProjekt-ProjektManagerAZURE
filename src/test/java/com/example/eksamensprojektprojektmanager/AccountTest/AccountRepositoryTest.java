package com.example.eksamensprojektprojektmanager.AccountTest;

import com.example.eksamensprojektprojektmanager.repository.AccountRepository;
import com.example.eksamensprojektprojektmanager.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

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

    // Removed @InjectMocks annotation
    private AccountService accountService;



    @Test
    public void testIsValidUser() {
        // Given
        String username = "testUser";
        String password = "testPassword";

        // Stubbing the jdbcTemplate's queryForObject method to return 1
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyString(), anyString()))
                .thenReturn(1); // Assuming the query returns a count of 1

        // When
        boolean isValid = accountRepository.isValidUser(username, password);

        // Then
        assertTrue(isValid);
        verify(jdbcTemplate).queryForObject(
                eq("SELECT COUNT(*) FROM users WHERE username = ? AND password = ?"),
                eq(Integer.class), eq(username), eq(password)
        );
    }
}