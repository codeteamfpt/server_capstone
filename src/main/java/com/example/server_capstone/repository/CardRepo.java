package com.example.server_capstone.repository;

import com.example.server_capstone.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends JpaRepository<CardEntity, Long> {
    CardEntity findByAccountId(Long accountId);
}
