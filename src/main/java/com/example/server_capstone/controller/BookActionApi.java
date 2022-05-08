package com.example.server_capstone.controller;

import com.example.server_capstone.dto.request.BookRequest;
import com.example.server_capstone.dto.response.BookResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
public interface BookActionApi {
    @GetMapping(value = "/book/getall")
    List<BookResponse> getBooks();

    @PostMapping(value = "/book/add")
    GeneralResponse addBook(@RequestBody BookRequest bookRequest);

    @PostMapping(value = "/book/delete")
    GeneralResponse deleteBook(@RequestBody BookRequest bookRequest);

    @PostMapping(value = "/book/update")
    GeneralResponse updateBook(@RequestBody BookRequest bookRequest);

    @PostMapping(value = "/book/getone")
    BookResponse getOneBook(BookRequest bookRequest);
}
