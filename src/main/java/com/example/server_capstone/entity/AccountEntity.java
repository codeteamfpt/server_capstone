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
@Table(name = "account")
public class AccountEntity {
    @Id
    @Column(name = "account_id")
    Long accountId;
    @Column(name = "user_name")
    String userName;
    @Column(name = "pass_word")
    String passWord;
    @Column(name = "role")
    boolean role;
    @Column(name = "user_image")
    String userImage;
}
