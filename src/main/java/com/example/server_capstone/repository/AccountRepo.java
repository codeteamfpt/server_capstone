package com.example.server_capstone.repository;

import com.example.server_capstone.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<AccountEntity,Long> {
}
