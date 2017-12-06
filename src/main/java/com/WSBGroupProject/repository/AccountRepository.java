package com.WSBGroupProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.WSBGroupProject.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findById(Long id);
    List<Account> findAll();
}