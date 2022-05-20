package com.example.server_capstone.repository;

import com.example.server_capstone.entity.CartInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface CartInfoRepo extends JpaRepository<CartInfoEntity, Long> {
    List<CartInfoEntity> findAllByCartId(Long cartId);

    CartInfoEntity findByBookIdAndCartId(Long bookId, Long cartId);

    void deleteByCartIdAndBookId(Long cartId, Long bookId);

    void deleteByCartId(Long cartId);

    @Modifying
    @Query(value = "INSERT INTO cart_info (cart_id, book_id,number_books ) VALUES ( :cartId, :bookId , :numberBooks )",
            nativeQuery = true)
    void addBook(@Param("cartId") Long cartId,
                 @Param("bookId") Long bookId,
                 @Param("numberBooks") Long numberBooks);


    @Query(value = "UPDATE cart_info SET number_books = :bookNumber WHERE book_id = :bookId AND cart_id = :cartId ",
            nativeQuery = true)
    void updateBook(@Param("bookNumber") Long bookNumber,
                    @Param("bookId") Long bookId,
                    @Param("cartId") Long cartId);
}
