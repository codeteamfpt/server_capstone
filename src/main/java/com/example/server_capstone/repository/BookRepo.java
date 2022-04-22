package com.example.server_capstone.repository;

import com.example.server_capstone.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<BookEntity,Long> {
    @Modifying
    @Query( value = "UPDATE book SET book_name = :bookName,book_info = :bookInfo," +
            "book_price = :bookPrice,book_type = :bookType WHERE book_id = :bookId",
            nativeQuery = true)
    void updateBook(@Param("bookId") Long bookId,
                    @Param("bookName") String bookName,
                    @Param("bookName") String bookInfo,
                    @Param("bookName") String bookPrice,
                    @Param("bookType") String bookType);

    @Query( value = "INSERT INTO book (book_name, book_info, book_price, book_type)" +
            "VALUES (:bookName, :bookInfo, :bookPrice, :bookType);",
            nativeQuery = true)

    void addBook(  @Param("bookName") String bookName,
                    @Param("bookInfo") String bookInfo,
                    @Param("bookPrice") String bookPrice,
                    @Param("bookType") String bookType);
}
