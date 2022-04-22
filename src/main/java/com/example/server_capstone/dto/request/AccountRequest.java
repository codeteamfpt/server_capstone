package com.example.server_capstone.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AccountRequest {
    @JsonProperty("accountId")
    Long accountId;
    @JsonProperty("userName")
    String userName;
    @JsonProperty("passWord")
    String passWord;
    @JsonProperty("role")
    Long role;
    @JsonProperty("userImage")
    String userImage;
}