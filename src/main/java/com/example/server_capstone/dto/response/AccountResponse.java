package com.example.server_capstone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountResponse {
    Long accountId;
    String userName;
    String passWord;
    Long role;
    String userImage;
}
