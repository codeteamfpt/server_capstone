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
        return responseList;
    }

    public AccountResponse getAccount(AccountRequest request) {
        Optional<AccountEntity> account = accountRepo.findById(request.getAccountId());
        AccountResponse response = new AccountResponse().builder()
                .accountId(account.get().getAccountId())
                .userName(account.get().getUserName())
                .passWord(account.get().getPassWord())
                .role(account.get().getRole())
                .userImage(account.get().getUserImage())
                .build();
        return response;
    }

    public AccountResponse checkAccount(AccountRequest request) {
        AccountEntity account = accountRepo.findByUserNameAndPassWord(request.getUserName(), request.getPassWord());
        if (account == null) return null;
        else {
            AccountResponse response = new AccountResponse().builder()
                    .accountId(account.getAccountId())
                    .passWord(account.getPassWord())
                    .userName(account.getUserName())
                    .role(account.getRole())
                    .userImage(account.getUserImage())
                    .build();
            return response;
        }

    }

    public AccountResponse regisAccount(AccountRequest request) {
        AccountEntity account = accountRepo.findByUserName(request.getUserName());
        if (account != null) {
            return null;
        } else {
            accountRepo.addAccount(request.getUserName(), request.getPassWord(), request.getRole(), request.getUserImage());
            AccountEntity entity = accountRepo.findByUserName(request.getUserName());
            AccountResponse response = new AccountResponse().builder()
                    .accountId(entity.getAccountId())
                    .userImage(entity.getUserImage())
                    .role(entity.getRole())
                    .userName(entity.getUserName())
                    .passWord(entity.getPassWord())
                    .build();
            return response;
        }

    }

    public GeneralResponse deleteAccount(AccountRequest request) {
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        Optional<AccountEntity> account = accountRepo.findById(request.getAccountId());
        if (account.isPresent()) {
            CartEntity cartEntity = cartRepo.findByAccountId(request.getAccountId());
            if (cartEntity != null) {
                if (!cartInfoRepo.findAllByCartId(cartEntity.getCartId()).isEmpty()) {
                    cartInfoRepo.deleteByCartId(cartEntity.getCartId());
                }
                cartRepo.deleteById(cartEntity.getCartId());
            }
            accountRepo.deleteById(request.getAccountId());
            statusResponse.setCode("00");
            statusResponse.setMessage("Success");
        } else {
            statusResponse.setCode("01");
            statusResponse.setMessage("Account Doesn't Exist");
        }
        response.setStatus(statusResponse);
        return response;
    }

    public GeneralResponse updateAccount(AccountRequest request) {
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        Optional<AccountEntity> account = accountRepo.findById(request.getAccountId());
        if (!account.isPresent()) {
            statusResponse.setCode("01");
            statusResponse.setMessage("Account Doesn't Exist");
        } else if (account.get().getRole().toString().equals("1")){
            statusResponse.setCode("02");
            statusResponse.setMessage("Account Admin cannot update");
        }else {
            accountRepo.updateAccount(request.getUserName(), request.getPassWord(), request.getRole(), request.getUserImage(), request.getAccountId());
            statusResponse.setCode("00");
            statusResponse.setMessage("Success");
        }
        response.setStatus(statusResponse);
        return response;
    }
}
