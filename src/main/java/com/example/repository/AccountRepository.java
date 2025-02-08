package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Spring Data repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    // Custom method to find an account by username
    Account findByUsername(String username);
}