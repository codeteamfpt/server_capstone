package com.example.server_capstone.repository;

import com.example.server_capstone.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<BookEntity,Long> {
}
