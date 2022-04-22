package com.example.server_capstone.service;

import com.example.server_capstone.dto.request.AccountRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.AccountResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import com.example.server_capstone.entity.AccountEntity;
import com.example.server_capstone.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountActionService {
    @Autowired
    AccountRepo accountRepo;

    public ListResponse<AccountResponse> getAllAccount() {
        List<AccountEntity> list = accountRepo.findAll();
        ListResponse<AccountResponse> accountResponseListResponse = new ListResponse<>();
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
        accountResponseListResponse.setList(responseList);
        return accountResponseListResponse;
    }
    public AccountResponse getAccount(AccountRequest request){
        Optional<AccountEntity> account = accountRepo.findById(request.getAccountId());
        AccountResponse response = new AccountResponse().builder()
                .userName(account.get().getUserName())
                .passWord(account.get().getPassWord())
                .role(account.get().getRole())
                .userImage(account.get().getUserImage())
                .build();
        return response;
    }

    public GeneralResponse checkAccount(AccountRequest request) {
        Optional<AccountEntity> account = accountRepo.findById(request.getAccountId());
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        if (account.isPresent()) {
            statusResponse.setCode("00");
            statusResponse.setMessage("Account Exist");
        } else {
            statusResponse.setCode("01");
            statusResponse.setMessage("Account Doesn't Exist");
        }
        response.setStatus(statusResponse);
        return response;
    }

    public GeneralResponse regisAccount(AccountRequest request) {
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        AccountEntity account = accountRepo.findByUserName(request.getUserName());
        if (account != null) {
            statusResponse.setCode("01");
            statusResponse.setMessage("Account Exist");
        } else {
            accountRepo.addAccount(request.getUserName(), request.getPassWord(), request.getRole(), request.getUserImage());
            statusResponse.setCode("00");
            statusResponse.setMessage("Success");
        }
        response.setStatus(statusResponse);
        return response;
    }

    public GeneralResponse deleteAccount(AccountRequest request) {
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        Optional<AccountEntity> account = accountRepo.findById(request.getAccountId());
        if (account.isPresent()) {
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

    public GeneralResponse updateAccount(AccountRequest request){
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        AccountEntity account = accountRepo.findByUserName(request.getUserName());
        if (account == null) {
            statusResponse.setCode("01");
            statusResponse.setMessage("Account Doesn't Exist");
        } else {
            accountRepo.updateAccount(request.getUserName(), request.getPassWord(), request.getRole(), request.getUserImage(),request.getAccountId());
            statusResponse.setCode("00");
            statusResponse.setMessage("Success");
        }
        response.setStatus(statusResponse);
        return response;
    }
}
