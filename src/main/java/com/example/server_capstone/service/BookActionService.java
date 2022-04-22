package com.example.server_capstone.service;

import com.example.server_capstone.dto.request.BookRequest;
import com.example.server_capstone.dto.response.BookResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import com.example.server_capstone.entity.BookEntity;
import com.example.server_capstone.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookActionService {
    @Autowired
    BookRepo bookRepo;


    public ListResponse<BookResponse> getAllBook() {
        List<BookEntity> bookList = bookRepo.findAll();
        List<BookResponse> bookResponseList = new ArrayList<>();
        for (BookEntity book : bookList
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

    public GeneralResponse updateBook(BookRequest bookRequest) {
        return actionBook(bookRequest,"update");
    }

    public GeneralResponse deleteBook(BookRequest bookRequest) {
        return actionBook(bookRequest,"delete");
    }

    public GeneralResponse addBook(BookRequest bookRequest) {
        return actionBook(bookRequest,"add");
    }

    GeneralResponse actionBook(BookRequest bookRequest, String action) {
        Long bookId = bookRequest.getBookId();
        Optional<BookEntity> book = bookRepo.findById(bookId);
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        if (!book.isPresent()) {
            statusResponse.setCode("001");
            statusResponse.setMessage("Can't find bookId");
            response.setStatus(statusResponse);
            return response;
        } else {
            if (action.equals("update")) {
                bookRepo.updateBook(bookRequest.getBookId(), bookRequest.getBookName(), bookRequest.getBookInfo(), bookRequest.getBookPrice(),bookRequest.getBookType());
            } else if (action.equals("delete")) {
                bookRepo.deleteById(bookRequest.getBookId());
            }else if (action.equals("add")) {
                bookRepo.addBook(bookRequest.getBookName(), bookRequest.getBookInfo(), bookRequest.getBookPrice(),bookRequest.getBookType());
            }else {
                statusResponse.setCode("002");
                statusResponse.setMessage("Wrong action !");
                response.setStatus(statusResponse);
                return response;
            }
            statusResponse.setCode("000");
            statusResponse.setMessage("Success");
            response.setStatus(statusResponse);
            return response;
        }


    }

}
