package com.example.server_capstone.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "book")
public class BookEntity {
    @Id
    @Column(name = "book_id")
    Long bookId;
    @Column(name = "book_name")
    String bookName;
    @Column(name = "book_info")
    String bookInfo;
    @Column(name = "book_price")
    String bookPrice;
    @Column(name = "book_type")
    String bookType;
    @Column(name = "book_image")
    String bookImage;
}
