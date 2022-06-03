package com.example.server_capstone.repository;

import com.example.server_capstone.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
// sử dụng JPA (phần kết nối backend với database) để thêm sửa xóa hoặc lấy thông tin từ database
public interface BookRepo extends JpaRepository<BookEntity, Long> {
    @Modifying
    @Query(value = "UPDATE book SET book_name = :bookName,book_info = :bookInfo," +
            "book_price = :bookPrice,book_type = :bookType,book_image = :bookImage WHERE book_id = :bookId",
            nativeQuery = true)
    void updateBook(@Param("bookId") Long bookId,
                    @Param("bookName") String bookName,
                    @Param("bookInfo") String bookInfo,
                    @Param("bookPrice") String bookPrice,
                    @Param("bookType") String bookType,
                    @Param("bookImage") String bookImage);

    @Query(value = "INSERT INTO book (book_name, book_info, book_price, book_type, book_image)" +
            "VALUES (:bookName, :bookInfo, :bookPrice, :bookType , :bookImage);",
            nativeQuery = true)
    void addBook(@Param("bookName") String bookName,
                 @Param("bookInfo") String bookInfo,
                 @Param("bookPrice") String bookPrice,
                 @Param("bookType") String bookType,
                 @Param("bookImage") String bookImage);
}
