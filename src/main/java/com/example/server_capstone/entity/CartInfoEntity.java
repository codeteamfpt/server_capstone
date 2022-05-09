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
@Builder
@Getter
@Setter
@Table(name = "cart_info")
public class CartInfoEntity {
    @Id
    @Column(name = "cart_info_id")
    Long cartInfoId;
    @Column(name = "cart_id")
    Long cartId;
    @Column(name = "book_id")
    Long bookId;
    @Column(name = "number_books")
    Long numberBook;
}
