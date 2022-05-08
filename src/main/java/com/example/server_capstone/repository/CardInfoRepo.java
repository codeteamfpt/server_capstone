package com.example.server_capstone.repository;

import com.example.server_capstone.entity.CardInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CardInfoRepo extends JpaRepository<CardInfoEntity, Long> {
    List<CardInfoEntity> findAllByCardId(Long cardId);

    CardInfoEntity findByBookIdAndCardId(Long bookId,Long cardId);
    void deleteByCardIdAndBookId(Long cardId, Long bookId);
    void deleteByCardId(Long cardId);
    @Modifying
    @Query(value = "INSERT INTO card_info (card_id, book_id,number_books ) VALUES ( :cardId, :bookId , :numberBooks )",
            nativeQuery = true)
    void addBook(@Param("cardId") Long cardId,
                 @Param("bookId") Long bookId,
                 @Param("numberBooks") Long numberBooks);


    @Query(value = "UPDATE card_info SET number_books = :bookNumber WHERE book_id = :bookId AND card_id = :cardId ",
            nativeQuery = true)
    void updateBook(@Param("bookNumber") Long bookNumber,
                    @Param("bookId") Long bookId,
                    @Param("cardId") Long cardId);
}
