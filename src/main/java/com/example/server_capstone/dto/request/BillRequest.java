package com.example.server_capstone.dto.request;

import lombok.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class BillRequest {
    Long accountId;
    String name;
    String email;
    String phoneNumber;
    String location;
    String totalBill;
}
