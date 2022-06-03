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
//Nơi xử các logic code, xử data get từ database

    public List<BookResponse> getAllBook() {
        // lấy thông tin tất các các sách trong database cho frontend
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
        // trả về một list sách
        return bookResponseList;
    }
// 3 hàm thêm sửa xóa sẽ gọi chung đến hàm actionBook để xử lý thêm sửa xóa
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
        // trường add là thêm sách
        if (action.equals("add")) {
            // thêm thông tin sách được frontend gửi vào database
            bookRepo.addBook(bookRequest.getBookName(), bookRequest.getBookInfo(), bookRequest.getBookPrice(), bookRequest.getBookType(), bookRequest.getBookImage());
        } else {
            // 2 trường hợp khác -> phải check xem có quyển sách đấy trong database không
            Long bookId = bookRequest.getBookId();
            Optional<BookEntity> book = bookRepo.findById(bookId);

            if (!book.isPresent()) {
                // nếu không có thì trả về thông báo không tìm thấy sách
                statusResponse.setCode("001");
                statusResponse.setMessage("Can't find bookId");
                response.setStatus(statusResponse);
                return response;
            } else {
                // trường hợp sửa sách và lưu vào database
                if (action.equals("update")) {
                    bookRepo.updateBook(bookRequest.getBookId(), bookRequest.getBookName(), bookRequest.getBookInfo(), bookRequest.getBookPrice(), bookRequest.getBookType(), bookRequest.getBookImage());

                // trường hợp xóa sách trong database
                } else if (action.equals("delete")) {
                    bookRepo.deleteById(bookRequest.getBookId());
                } else {
                    // nếu không phải chức năng thêm sửa xóa thì sẽ báo là sai chức năng
                    statusResponse.setCode("002");
                    statusResponse.setMessage("Wrong action !");
                    response.setStatus(statusResponse);
                    return response;
                }

            }
        }
        // trường hợp thêm sửa xóa thành công thì trả về thông báo thành công
        statusResponse.setCode("000");
        statusResponse.setMessage("Success");
        response.setStatus(statusResponse);
        return response;
    }

    public BookResponse getOneBook(BookRequest bookRequest) {
        // để lấy thông tin toàn bộ của 1 quyển sách
        if (bookRequest.getBookId() == null) {
            // nếu id truyền vào là rỗng - trống đấy thì trả về 1 quyển sách không có 1 thông tin gì
            return BookResponse.builder()
                    .bookId(0L)
                    .build();
        } else {
            Long bookId = bookRequest.getBookId();
            // nếu không tìm thấy quyển sách đấy thì trả về 1 quyển sách không có 1 thông tin gì
            Optional<BookEntity> book = bookRepo.findById(bookId);
            if (!book.isPresent()) {
                return BookResponse.builder()
                        .bookId(0L)
                        .build();
            } else {
                // nếu tìm thấy sách thì trả về thông tin đầy đủ của quyển sách đấy
                return BookResponse.builder()
                        .bookId(book.get().getBookId())
                        .bookType(book.get().getBookType())
                        .bookInfo(book.get().getBookInfo())
                        .bookName(book.get().getBookName())
                        .bookPrice(book.get().getBookPrice())
                        .bookImage(book.get().getBookImage())
                        .build();

            }
        }
    }
}
