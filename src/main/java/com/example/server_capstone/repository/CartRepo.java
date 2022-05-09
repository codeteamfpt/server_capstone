package com.example.server_capstone.repository;

import com.example.server_capstone.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CartRepo extends JpaRepository<CartEntity, Long> {
    CartEntity findByAccountId(Long accountId);
}
