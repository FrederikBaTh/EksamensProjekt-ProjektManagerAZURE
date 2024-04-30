package com.example.eksamensprojektprojektmanager.service;

import com.example.eksamensprojektprojektmanager.model.Account;
import com.example.eksamensprojektprojektmanager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;


    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;


    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public boolean isValidUser(String username, String password) {
        return accountRepository.isValidUser(username, password);
    }

    public String getUserIdByUsername(String username) {
        return accountRepository.getUserIdByUsername(username);
    }
}



