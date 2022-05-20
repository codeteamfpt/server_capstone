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
public class GeneralResponse {
    StatusResponse status;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class StatusResponse {
        String code;
        String message;
    }

}
