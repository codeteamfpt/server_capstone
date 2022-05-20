package com.example.server_capstone.repository;

import com.example.server_capstone.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface BillRepo extends JpaRepository<BillEntity, Long> {
    List<BillEntity> findAllByAccountId(Long accountId);
}
