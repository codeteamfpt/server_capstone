package com.example.server_capstone.dto.request;

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
public class BookRequest {
    Long bookId;
    String bookName;
    String bookInfo;
    String bookPrice;
    String bookType;
    String bookImage;
}
