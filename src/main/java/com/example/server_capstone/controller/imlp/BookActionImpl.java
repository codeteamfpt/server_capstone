package com.example.server_capstone.controller.imlp;

import com.example.server_capstone.controller.BookActionApi;
import com.example.server_capstone.dto.request.BookRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.BookResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import com.example.server_capstone.service.BookActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookActionImpl implements BookActionApi {
    @Autowired
    BookActionService bookActionService;


    @Override
    public ListResponse<BookResponse> getBooks() {
        return bookActionService.getAllBook();
    }

    @Override
    public GeneralResponse addBook(BookRequest bookRequest) {
        return bookActionService.addBook(bookRequest);
    }

    @Override
    public GeneralResponse deleteBook(BookRequest bookRequest) {
        return bookActionService.deleteBook(bookRequest);
    }

    @Override
    public GeneralResponse updateBook(BookRequest bookRequest) {
        return bookActionService.updateBook(bookRequest);
    }

    @Override
    public String updateBook() {
        return "123123";
    }
}
