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
public class CartResponse {
    Long bookId;
    String bookName;
    String bookInfo;
    String bookPrice;
    String bookType;
    Long numberBook;
    String bookImage;
}
