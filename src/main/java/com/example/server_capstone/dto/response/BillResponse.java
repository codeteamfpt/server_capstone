package com.example.server_capstone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BillResponse {
    Long billId;
    String name;
    String email;
    String phoneNumber;
    String location;
    String totalBill;
    Date createDate;
    String status;
}
