package com.example.server_capstone.controller.imlp;

import com.example.server_capstone.controller.AccountActionApi;
import com.example.server_capstone.dto.request.AccountRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.AccountResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountActionImpl implements AccountActionApi {

    @Override
    public ListResponse<AccountResponse> getAllAccount(GetAllRequest request) {
        return null;
    }

    @Override
    public GeneralResponse checkAccount(AccountRequest request) {
        return null;
    }

    @Override
    public GeneralResponse regisAccount(AccountRequest request) {
        return null;
    }

    @Override
    public GeneralResponse deleteAccount(AccountRequest request) {
        return null;
    }

    @Override
    public GeneralResponse updateAccount(AccountRequest request) {
        return null;
    }
}
