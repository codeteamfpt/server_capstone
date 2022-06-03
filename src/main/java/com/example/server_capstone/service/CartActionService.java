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
//Nơi xử các logic code, xử data get từ database
// phần này dành cho giỏ hàng
    public GeneralResponse createCart(CartRequest request) {
        // tạo giỏ hàng -> frontend gửi vào accountId để tạo giỏ hàng ứng với account được gửi
        // kiểm tra xem account này có giỏ hàng nào chưa - phòng trường hợp tự nhập account thông qua database không thông frontend
        CartEntity cartId = cartRepo.findByAccountId(request.getAccountId());
        CartEntity cartEntity = new CartEntity().builder()
                .accountId(request.getAccountId())
                .build();
        if (cartId == null) {
            // chưa có giỏ hàng thì sẽ thêm giỏ hàng cho người dùng
            cartRepo.save(cartEntity);
        } else {
            // nếu đã có giỏ hàng sẽ xóa thông tin của giỏ hàng đấy rồi xóa luôn giỏ hàng và tạo 1 giỏ hàng mới
            cartInfoRepo.deleteByCartId(cartId.getCartId());
            cartRepo.deleteById(cartId.getCartId());
            cartRepo.save(cartEntity);
        }
        // sau khi đã thêm giỏ hàng thành công thì thông báo cho frontend
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        statusResponse.setCode("000");
        statusResponse.setMessage("Success");
        response.setStatus(statusResponse);
        return response;
    }

    public List<CartResponse> getCart(GetAllRequest cartRequest) {
        // lấy thông tin giỏ hàng hiển thị lên database
        // lấy mã số giỏ hàng của người dùng tương ứng với accountId mà frontend truyền vào
        CartEntity cartId = cartRepo.findByAccountId(cartRequest.getAccountId());
        // lấy list thông tin giỏ hàng tương ứng với giỏ hàng của người dùng
        List<CartInfoEntity> cartInfoEntities = cartInfoRepo.findAllByCartId(cartId.getCartId());
        // chức năng từ dòng 71-84 là để lưu vào response trả về cho frontend
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
                    .bookImage(book.get().getBookImage())
                    .build();
            cartResponses.add(cart);
        }
        // trả về 1 list sách có trong giỏ hàng
        return cartResponses;
    }


    public double payCart(GetAllRequest cartRequest) {
        // tính tổng tiền của sản phẩm trong giỏ hàng
        // lấy mã số giỏ hàng của người dùng tương ứng với accountId mà frontend truyền vào
        CartEntity cartId = cartRepo.findByAccountId(cartRequest.getAccountId());
        // lấy list thông tin giỏ hàng tương ứng với giỏ hàng của người dùng
        List<CartInfoEntity> cartInfoEntities = cartInfoRepo.findAllByCartId(cartId.getCartId());
        double price = 0;
        for (CartInfoEntity c : cartInfoEntities
        ) {
            // tính tiền từng sản phẩm và cộng vào
            Optional<BookEntity> book = bookRepo.findById(c.getBookId());
            price += Double.parseDouble(book.get().getBookPrice()) * Double.parseDouble(c.getNumberBook().toString());
        }
        // trả về tổng tiền cần trả
        return price;
    }
// 3 hàm thêm sửa xóa thông tin giỏ hàng
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
        // lấy mã số giỏ hàng của người dùng tương ứng với accountId mà frontend truyền vào
        CartEntity cartId = cartRepo.findByAccountId(cartRequest.getAccountId());
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        if (cartId == null) {
            // không tìm thấy giỏ hàng của người dùng -> thông báo frontend
            statusResponse.setCode("001");
            statusResponse.setMessage("Can't find cartId");
            response.setStatus(statusResponse);
            return response;
        } else {
            // tìm thấy giỏ hàng
            if (action.equals("update")) {
                //update số lượng sách của 1 loại sách trong giỏ hàng
                cartInfoRepo.updateBook(cartRequest.getNumberBook(), cartRequest.getBookId(), cartId.getCartId());
            } else if (action.equals("delete")) {
                //xóa tất cả sách của 1 loại sách trong giỏ hàng
                cartInfoRepo.deleteByCartIdAndBookId(cartId.getCartId(), cartRequest.getBookId());
            } else if (action.equals("add")) {
                // thêm 1 quyển sách mới vào giỏ hàng
                // kiểm tra xem đã có quyển sách trùng chưa
                CartInfoEntity entity = cartInfoRepo.findByBookIdAndCartId(cartRequest.getBookId(), cartId.getCartId());
                if (entity != null) {
                    // nếu đã có quyển sách đấy thì tăng số lượng sách trong giỏ hàng
                    cartRequest.setNumberBook(entity.getNumberBook() + cartRequest.getNumberBook());
                    actioncart(cartRequest, "update");
                } else {
                    // nếu chưa có thì thêm vào giỏ hàng loại sách mới
                    cartInfoRepo.addBook(cartId.getCartId(), cartRequest.getBookId(), cartRequest.getNumberBook());
                }
            } else {
                // sai chức năng
                statusResponse.setCode("002");
                statusResponse.setMessage("Wrong action !");
                response.setStatus(statusResponse);
                return response;
            }
            // thực hiện chức năng thành công -> thông báo frontend
            statusResponse.setCode("000");
            statusResponse.setMessage("Success");
            response.setStatus(statusResponse);
            return response;
        }


    }
// phần này dành cho hóa đơn
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
            // lưu thông tin hóa đơn được nhập từ màn hình
            billRepo.save(billEntity);
            GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse().builder()
                    .code("00")
                    .message("Save Success")
                    .build();
            // hiện ra thông báo lưu thành công
            response.setStatus(statusResponse);
        }catch (Exception e){
            // lưu hóa đơn thất bại
            // trả về lý do lưu thất bại cho frontend
            GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse().builder()
                    .code("01")
                    .message("Fail "+e.getMessage())
                    .build();
            response.setStatus(statusResponse);
        }
        return response;
    }

    public List<BillResponse> getBillByAccount(GetAllRequest request){
        // lấy thông tin hóa đơn đã thực hiện
        // lấy list tất cả các hóa đơn của 1 người dùng
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
            // trả lại 1 list hóa đơn cảu người dùng
            return list;
        }else {
            // không trả lại gì cả nếu không có hóa đơn nào
            return null;
        }
    }
}
