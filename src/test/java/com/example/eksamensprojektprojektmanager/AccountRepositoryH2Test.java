package com.example.eksamensprojektprojektmanager;


import com.example.eksamensprojektprojektmanager.model.Account;
import com.example.eksamensprojektprojektmanager.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("h2")
public class AccountRepositoryH2Test {


    @Autowired
    AccountRepository repository;


    @Test
    void findAccount(){
        Account found = repository.getUserById(1L);
        assertEquals("testUser1",found.getUsername());
    }



}
