package com.example.server_capstone.service;

import com.example.server_capstone.dto.request.BookRequest;
import com.example.server_capstone.dto.response.BookResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.entity.BookEntity;
import com.example.server_capstone.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookActionService {
    @Autowired
    BookRepo bookRepo;


    public List<BookResponse> getAllBook() {
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
                    .bookImage(book.getBookImage())
                    .build();
            bookResponseList.add(bookResponse);
        }
        return bookResponseList;
    }

    public GeneralResponse updateBook(BookRequest bookRequest) {
        return actionBook(bookRequest, "update");
    }

    public GeneralResponse deleteBook(BookRequest bookRequest) {
        return actionBook(bookRequest, "delete");
    }

    public GeneralResponse addBook(BookRequest bookRequest) {
        return actionBook(bookRequest, "add");
    }

    GeneralResponse actionBook(BookRequest bookRequest, String action) {

        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        if (action.equals("add")) {
            bookRepo.addBook(bookRequest.getBookName(), bookRequest.getBookInfo(), bookRequest.getBookPrice(), bookRequest.getBookType(), bookRequest.getBookImage());
        } else {
            Long bookId = bookRequest.getBookId();
            Optional<BookEntity> book = bookRepo.findById(bookId);

            if (!book.isPresent()) {
                statusResponse.setCode("001");
                statusResponse.setMessage("Can't find bookId");
                response.setStatus(statusResponse);
                return response;
            } else {
                if (action.equals("update")) {
                    bookRepo.updateBook(bookRequest.getBookId(), bookRequest.getBookName(), bookRequest.getBookInfo(), bookRequest.getBookPrice(), bookRequest.getBookType(), bookRequest.getBookImage());
                } else if (action.equals("delete")) {
                    bookRepo.deleteById(bookRequest.getBookId());
                } else {
                    statusResponse.setCode("002");
                    statusResponse.setMessage("Wrong action !");
                    response.setStatus(statusResponse);
                    return response;
                }

            }
        }
        statusResponse.setCode("000");
        statusResponse.setMessage("Success");
        response.setStatus(statusResponse);
        return response;
    }

    public BookResponse getOneBook(BookRequest bookRequest) {
        if (bookRequest.getBookId() == null) {
            return BookResponse.builder()
                    .bookId(0L)
                    .build();
        } else {
            Long bookId = bookRequest.getBookId();
            Optional<BookEntity> book = bookRepo.findById(bookId);
            if (!book.isPresent()) {
                return BookResponse.builder()
                        .bookId(0L)
                        .build();
            } else {
                return BookResponse.builder()
                        .bookId(book.get().getBookId())
                        .bookType(book.get().getBookType())
                        .bookInfo(book.get().getBookInfo())
                        .bookName(book.get().getBookName())
                        .bookPrice(book.get().getBookPrice())
                        .bookInfo(book.get().getBookInfo())
                        .bookImage(book.get().getBookImage())
                        .build();

            }
        }
    }
}
