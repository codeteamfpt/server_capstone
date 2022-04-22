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
    Long role;
    @Column(name = "user_image")
    String userImage;
}
