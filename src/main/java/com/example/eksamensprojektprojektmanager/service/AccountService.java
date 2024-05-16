package com.example.eksamensprojektprojektmanager.service;

import com.example.eksamensprojektprojektmanager.model.Account;
import com.example.eksamensprojektprojektmanager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {



    private AccountRepository accountRepository;


    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


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

    public List<Account> getAllUsers() {
        return accountRepository.getAllUsers();
    }

    public Account getUserById(Long userId) {
        return accountRepository.getUserById(userId);
    }
    public List<Account> getUsersByIdsList(List<Long> userIds) {
        List<Account> users = new ArrayList<>();
        for (Long userId : userIds) {
            users.add(getUserById(userId));
        }
        return users;
    }

    public boolean usernameExists(String username) {
        return accountRepository.usernameExists(username);
    }

    public void updateAccount(Account account) {
        accountRepository.updateAccount(account);
    }



}



