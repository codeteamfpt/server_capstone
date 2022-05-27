package com.example.server_capstone.service;

import com.example.server_capstone.dto.request.BillRequest;
import com.example.server_capstone.dto.request.CartRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.BillResponse;
import com.example.server_capstone.dto.response.CartResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import com.example.server_capstone.entity.BillEntity;
import com.example.server_capstone.entity.BookEntity;
import com.example.server_capstone.entity.CartEntity;
import com.example.server_capstone.entity.CartInfoEntity;
import com.example.server_capstone.repository.BillRepo;
import com.example.server_capstone.repository.BookRepo;
import com.example.server_capstone.repository.CartInfoRepo;
import com.example.server_capstone.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartActionService {
    @Autowired
    CartRepo cartRepo;
    @Autowired
    CartInfoRepo cartInfoRepo;
    @Autowired
    BookRepo bookRepo;
    @Autowired
    BillRepo billRepo;

    public GeneralResponse createCart(CartRequest request) {
        CartEntity cartId = cartRepo.findByAccountId(request.getAccountId());
        CartEntity cartEntity = new CartEntity().builder()
                .accountId(request.getAccountId())
                .build();
        if (cartId == null) {
            cartRepo.save(cartEntity);
        } else {
            cartInfoRepo.deleteByCartId(cartId.getCartId());
            cartRepo.deleteById(cartId.getCartId());
            cartRepo.save(cartEntity);
        }
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        statusResponse.setCode("000");
        statusResponse.setMessage("Success");
        response.setStatus(statusResponse);
        return response;
    }

    public List<CartResponse> getCart(GetAllRequest cartRequest) {
        CartEntity cartId = cartRepo.findByAccountId(cartRequest.getAccountId());
        List<CartInfoEntity> cartInfoEntities = cartInfoRepo.findAllByCartId(cartId.getCartId());
        List<CartResponse> cartResponses = new ArrayList<>();
        for (CartInfoEntity cartInfoEntity : cartInfoEntities
        ) {
            Optional<BookEntity> book = bookRepo.findById(cartInfoEntity.getBookId());
            CartResponse cart = new CartResponse().builder()
                    .bookId(book.get().getBookId())
                    .bookName(book.get().getBookName())
                    .numberBook(cartInfoEntity.getNumberBook())
                    .bookInfo(book.get().getBookInfo())
                    .bookPrice(book.get().getBookPrice())
                    .bookType(book.get().getBookType())
                    .build();
            cartResponses.add(cart);
        }
        return cartResponses;
    }


    public double payCart(GetAllRequest cartRequest) {
        CartEntity cartId = cartRepo.findByAccountId(cartRequest.getAccountId());
        List<CartInfoEntity> cartInfoEntities = cartInfoRepo.findAllByCartId(cartId.getCartId());
        double price = 0;
        for (CartInfoEntity c : cartInfoEntities
        ) {
            Optional<BookEntity> book = bookRepo.findById(c.getBookId());
            price += Double.parseDouble(book.get().getBookPrice()) * Double.parseDouble(c.getNumberBook().toString());
        }
        return price;
    }

    public GeneralResponse updateCart(CartRequest cartRequest) {
        return actioncart(cartRequest, "update");
    }

    public GeneralResponse deleteCart(CartRequest cartRequest) {
        return actioncart(cartRequest, "delete");
    }

    public GeneralResponse addCart(CartRequest cartRequest) {
        return actioncart(cartRequest, "add");
    }

    GeneralResponse actioncart(CartRequest cartRequest, String action) {
        CartEntity cartId = cartRepo.findByAccountId(cartRequest.getAccountId());
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        if (cartId == null) {
            statusResponse.setCode("001");
            statusResponse.setMessage("Can't find cartId");
            response.setStatus(statusResponse);
            return response;
        } else {
            if (action.equals("update")) {
                cartInfoRepo.updateBook(cartRequest.getNumberBook(), cartRequest.getBookId(), cartId.getCartId());
            } else if (action.equals("delete")) {
                cartInfoRepo.deleteByCartIdAndBookId(cartId.getCartId(), cartRequest.getBookId());
            } else if (action.equals("add")) {
                CartInfoEntity entity = cartInfoRepo.findByBookIdAndCartId(cartRequest.getBookId(), cartId.getCartId());
                if (entity != null) {
                    cartRequest.setNumberBook(entity.getNumberBook() + cartRequest.getNumberBook());
                    actioncart(cartRequest, "update");
                } else {
                    cartInfoRepo.addBook(cartId.getCartId(), cartRequest.getBookId(), cartRequest.getNumberBook());
                }
            } else {
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

    public GeneralResponse billSave(BillRequest request){
        GeneralResponse response = new GeneralResponse();
        try{
            BillEntity billEntity = new BillEntity().builder()
                    .accountId(request.getAccountId())
                    .name(request.getName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .location(request.getLocation())
                    .totalBill(request.getTotalBill())
                    .createDate(new Date())
                    .status("In Process")
                    .build();
            billRepo.save(billEntity);
            GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse().builder()
                    .code("00")
                    .message("Save Success")
                    .build();
            response.setStatus(statusResponse);
        }catch (Exception e){
            GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse().builder()
                    .code("01")
                    .message("Fail "+e.getMessage())
                    .build();
            response.setStatus(statusResponse);
        }
        return response;
    }

    public List<BillResponse> getBillByAccount(GetAllRequest request){
        List<BillEntity> bills = billRepo.findAllByAccountId(request.getAccountId());
        if(!bills.isEmpty()){
            List<BillResponse> list = new ArrayList<>();
            for (BillEntity bill : bills
                 ) {
                BillResponse response = new BillResponse().builder()
                        .billId(bill.getBillId())
                        .name(bill.getName())
                        .email(bill.getEmail())
                        .location(bill.getLocation())
                        .createDate(new SimpleDateFormat("dd/MM/yyyy").format(bill.getCreateDate()))
                        .status(bill.getStatus())
                        .phoneNumber(bill.getPhoneNumber())
                        .totalBill(bill.getTotalBill())
                        .build();
                list.add(response);
            }
            return list;
        }else {
            return null;
        }
    }
}
