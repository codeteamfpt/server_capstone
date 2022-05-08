package com.example.server_capstone.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
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