package com.example.server_capstone.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Setter
@Getter
@Table(name = "card")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    Long cardId;
    @Column(name = "account_id")
    Long accountId;
}
