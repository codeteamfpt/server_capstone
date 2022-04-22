package com.example.server_capstone.repository;

import com.example.server_capstone.entity.CardInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardInfoRepo extends JpaRepository<CardInfoEntity, Long> {
    List<CardInfoEntity> findAllByCardId(Long cardId);

    void deleteByCardIdAndBookId(Long cardId, Long bookId);

    @Modifying
    @Query(value = "INSERT INTO card_info (card_id, number_books, book_id)" +
            "VALUES (:cardId, :numberBooks, :bookId);",
            nativeQuery = true)
    void addBook(@Param("cardId") Long cardId,
                 @Param("numberBooks") Long numberBooks,
                 @Param("bookId") Long bookId);

    @Query(value = "UPDATE card_info SET number_books = :bookNumber WHERE book_id = :bookId AND card_id = :cardId ",
            nativeQuery = true)
    void updateBook(@Param("bookNumber") Long bookNumber,
                    @Param("bookId") Long bookId,
                    @Param("cardId") Long cardId);
}
