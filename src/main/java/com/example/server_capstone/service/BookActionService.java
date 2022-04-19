package com.example.server_capstone.service;

import com.example.server_capstone.dto.request.BookRequest;
import com.example.server_capstone.dto.response.BookResponse;
import com.example.server_capstone.dto.response.ListResponse;
import com.example.server_capstone.entity.BookEntity;
import com.example.server_capstone.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookActionService {
    @Autowired
    BookRepo bookRepo;


    public ListResponse<BookResponse> getAllBook(){
        List<BookEntity> bookList = bookRepo.findAll();
        List<BookResponse> bookResponseList = new ArrayList<>();
        for (BookEntity book: bookList
             ) {
            BookResponse bookResponse = BookResponse.builder()
                    .bookId(book.getBookId())
                    .bookInfo(book.getBookInfo())
                    .bookName(book.getBookName())
                    .bookPrice(book.getBookPrice())
                    .bookType(book.getBookType())
                    .build();
            bookResponseList.add(bookResponse);
        }
        ListResponse<BookResponse> bookResponseListResponse = new ListResponse<>();
        bookResponseListResponse.setList(bookResponseList);
        return bookResponseListResponse;
    }
}
