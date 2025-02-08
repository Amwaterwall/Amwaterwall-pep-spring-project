package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Spring service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired 
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Registers a new account
    public ResponseEntity<?> registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.badRequest().build(); // Return 400 if validation fails
        }
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(409).build(); // Return 409 if username already exists
        }
        return ResponseEntity.ok(accountRepository.save(account)); // Save and return the new account
    }

    // Authenticates a user login
    public ResponseEntity<?> login(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return ResponseEntity.ok(existingAccount); // Return account details if login is successful
        }
        return ResponseEntity.status(401).build(); // Return 401 if authentication fails
    }
}