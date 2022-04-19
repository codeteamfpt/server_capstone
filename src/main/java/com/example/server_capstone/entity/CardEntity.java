package com.example.server_capstone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "card")
public class CardEntity {
    @Id
    @Column(name = "card_id")
    Long cardId;
    @Column(name = "account_id")
    Long accountId;
}
