package com.example.server_capstone.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Setter
@Table(name = "bill")
public class BillEntity {
    @Id
    @Column(name = "bill_id")
    Long billId;
    @Column(name = "account_id")
    Long accountId;
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
    @Column(name = "phone_number")
    String phoneNumber;
    @Column(name = "location")
    String location;
    @Column(name = "total_bill")
    String totalBill;
    @Column(name = "create_date")
    Date createDate;
    @Column(name = "status")
    String status;
}