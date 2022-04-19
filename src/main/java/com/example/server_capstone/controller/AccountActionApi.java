package com.example.server_capstone.controller;
import com.example.server_capstone.dto.request.AccountRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.AccountResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api")
public interface AccountActionApi {
    @GetMapping(value = "/account/all")
    ListResponse<AccountResponse> getAllAccount(GetAllRequest request);

    @GetMapping(value = "/account/check")
    GeneralResponse checkAccount(AccountRequest request);

    @GetMapping(value = "/account/regis")
    GeneralResponse regisAccount(AccountRequest request);

    @GetMapping(value = "/account/delete")
    GeneralResponse deleteAccount(AccountRequest request);

    @GetMapping(value = "/account/update")
    GeneralResponse updateAccount(AccountRequest request);
}
