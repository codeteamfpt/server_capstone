package com.example.server_capstone.repository;

import com.example.server_capstone.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepo extends JpaRepository<CardEntity,Long> {
}
