package com.example.server_capstone.service;

import com.example.server_capstone.dto.request.AccountRequest;
import com.example.server_capstone.dto.response.AccountResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.entity.AccountEntity;
import com.example.server_capstone.entity.CartEntity;
import com.example.server_capstone.repository.AccountRepo;
import com.example.server_capstone.repository.CartInfoRepo;
import com.example.server_capstone.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountActionService {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    CartInfoRepo cartInfoRepo;

    public List<AccountResponse> getAllAccount() {
        // lấy tất cả các account có trong database để trả về cho frontend
        List<AccountEntity> list = accountRepo.findAll();
        List<AccountResponse> responseList = new ArrayList<>();
        for (AccountEntity entity : list
        ) {
            AccountResponse account = new AccountResponse().builder()
                    .accountId(entity.getAccountId())
                    .userName(entity.getUserName())
                    .passWord(entity.getPassWord())
                    .role(entity.getRole())
                    .userImage(entity.getUserImage())
                    .build();
            responseList.add(account);
        }
        // trả về một list các account
        return responseList;
    }

    public AccountResponse getAccount(AccountRequest request) {
        // frontend gửi accountID cho backend -> backend sẽ kiểm tra trong database có accountId đấy không
        Optional<AccountEntity> account = accountRepo.findById(request.getAccountId());
        AccountResponse response = new AccountResponse().builder()
                .accountId(account.get().getAccountId())
                .userName(account.get().getUserName())
                .passWord(account.get().getPassWord())
                .role(account.get().getRole())
                .userImage(account.get().getUserImage())
                .build();
        //trả về thông tin của account đấy cho frontend :  nếu như không có account này thì sẽ trả về 1 cái account chứa tất cả các thông tin là null
        return response;
    }

    public AccountResponse checkAccount(AccountRequest request) {
        // tác dụng kiểm tra xem user password khi đăng nhập
        // tìm trong database 1 account chưa username và password trùng với username và password frontend gửi
        AccountEntity account = accountRepo.findByUserNameAndPassWord(request.getUserName(), request.getPassWord());
        // nếu không có sẽ không trả về cái gì
        if (account == null) return null;
        else {
            AccountResponse response = new AccountResponse().builder()
                    .accountId(account.getAccountId())
                    .passWord(account.getPassWord())
                    .userName(account.getUserName())
                    .role(account.getRole())
                    .userImage(account.getUserImage())
                    .build();
            // nếu có thì sẽ trả về thông tin đầy đủ của 1 account -> để frontend biết là đã đăng nhập đúng username và password
            return response;
        }

    }

    public AccountResponse regisAccount(AccountRequest request) {
        // đăng ký account cho người dùng
        // nhận các thông tin về account như tên, mk, ảnh -> lưu vào database
        // kiểm tra xem username đã tồn tại trong database chưa
        AccountEntity account = accountRepo.findByUserName(request.getUserName());
        if (account != null) {
            // username đã tồn tại -> không trả về gì cả -> frontend sẽ tự show cho người dùng nhập lại
            return null;
        } else {
            // nếu username chưa tồn tại thì sẽ lưu các thông tin người dùng đã nhập vào database
            accountRepo.addAccount(request.getUserName(), request.getPassWord(), request.getRole(), request.getUserImage());
            AccountEntity entity = accountRepo.findByUserName(request.getUserName());
            AccountResponse response = new AccountResponse().builder()
                    .accountId(entity.getAccountId())
                    .userImage(entity.getUserImage())
                    .role(entity.getRole())
                    .userName(entity.getUserName())
                    .passWord(entity.getPassWord())
                    .build();
            // trả về cho frontend thông tin đầy đủ vừa nhập vào
            return response;
        }

    }

    public GeneralResponse deleteAccount(AccountRequest request) {
        // xóa account
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        // kiểm tra xem account có tồn tại không
        Optional<AccountEntity> account = accountRepo.findById(request.getAccountId());
        if (account.isPresent()) {
            // account có tồn tại thì sẽ xóa thông tin của giỏ hàng sau đấy xóa giỏ hàng rồi mới xóa account
            // kiểm tra xem người dùng đã có giỏ hàng nào chưa
            CartEntity cartEntity = cartRepo.findByAccountId(request.getAccountId());
            if (cartEntity != null) {
                // trường hợp người dùng có giỏ hàng thì kiểm tra giỏ hàng có thông tin bên trong không
                if (!cartInfoRepo.findAllByCartId(cartEntity.getCartId()).isEmpty()) {
                    // trường hợp có thông tin thì sẽ xóa thông tin giỏ hàng trước
                    cartInfoRepo.deleteByCartId(cartEntity.getCartId());
                }
                // xóa giỏ hàng
                cartRepo.deleteById(cartEntity.getCartId());
            }
            // xóa account
            accountRepo.deleteById(request.getAccountId());
            // trả về cho frontend thông báo đã xóa thành công
            statusResponse.setCode("00");
            statusResponse.setMessage("Success");
        } else {
            // trường hợp account không tồn tại thông báo không có account
            statusResponse.setCode("01");
            statusResponse.setMessage("Account Doesn't Exist");
        }
        // trả về thông báo được đặt ở bên trên theo 2 trường hợp
        response.setStatus(statusResponse);
        return response;
    }

    public GeneralResponse updateAccount(AccountRequest request) {
        // sửa thông tin account
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        // kiểm tra có account này không
        Optional<AccountEntity> account = accountRepo.findById(request.getAccountId());
        if (!account.isPresent()) {
            // nếu không tồn tại thông báo không có account
            statusResponse.setCode("01");
            statusResponse.setMessage("Account Doesn't Exist");
        } else if (account.get().getRole().toString().equals("1")){
            // trường hợp account là account admin -> thông báo không được phép thay đổi
            statusResponse.setCode("02");
            statusResponse.setMessage("Account Admin cannot update");
        }else {
            // trường hợp update account
            accountRepo.updateAccount(request.getUserName(), request.getPassWord(), request.getRole(), request.getUserImage(), request.getAccountId());
            // sau khi sửa sẽ thông báo sửa thành công
            statusResponse.setCode("00");
            statusResponse.setMessage("Success");
        }
        response.setStatus(statusResponse);
        return response;
    }
}
