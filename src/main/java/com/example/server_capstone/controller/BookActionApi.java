package com.example.server_capstone.controller;

import com.example.server_capstone.dto.request.BookRequest;
import com.example.server_capstone.dto.response.BookResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface BookActionApi {
    @GetMapping(value = "/book/getall")
    ListResponse<BookResponse> getBooks();

    @GetMapping(value = "/book/add")
    GeneralResponse addBook(@RequestBody BookRequest bookRequest);

    @GetMapping(value = "/book/delete")
    GeneralResponse deleteBook(@RequestBody BookRequest bookRequest);

    @GetMapping(value = "/book/update")
    GeneralResponse updateBook(@RequestBody BookRequest bookRequest);

    @GetMapping(value = "/book/1")
    String updateBook();
}
