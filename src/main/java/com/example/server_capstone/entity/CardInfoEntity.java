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
@Table(name = "card_info")
public class CardInfoEntity {
    @Id
    @Column(name = "card_info_id")
    Long cardInfoId;
    @Column(name = "card_id")
    Long cardId;
    @Column(name = "book_id")
    Long bookId;
    @Column(name = "number_books")
    Long numberBook;
}
