package com.example.server_capstone.dto.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookResponse {
    Long bookId;
    String bookName;
    String bookInfo;
    String bookPrice;
    String bookType;
}
