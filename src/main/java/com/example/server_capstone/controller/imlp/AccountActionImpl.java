package com.example.server_capstone.controller.imlp;

import com.example.server_capstone.controller.AccountActionApi;
import com.example.server_capstone.dto.request.AccountRequest;
import com.example.server_capstone.dto.response.AccountResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.service.AccountActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountActionImpl implements AccountActionApi {

    @Autowired
    AccountActionService accountActionService;

    @Override
    public List<AccountResponse> getAllAccount() {
        return accountActionService.getAllAccount();
    }

    @Override
    public AccountResponse checkAccount(AccountRequest request) {
        return accountActionService.checkAccount(request);
    }


    @Override
    public AccountResponse regisAccount(AccountRequest request) {
        return accountActionService.regisAccount(request);
    }

    @Override
    public GeneralResponse deleteAccount(AccountRequest request) {
        return accountActionService.deleteAccount(request);
    }

    @Override
    public GeneralResponse updateAccount(AccountRequest request) {
        return accountActionService.updateAccount(request);
    }

    @Override
    public AccountResponse getAccount(AccountRequest request) {
        return accountActionService.getAccount(request);
    }

    @Override
    public GeneralResponse regisAccount(String check) {
        System.out.println(check);
        return null;
    }
}
