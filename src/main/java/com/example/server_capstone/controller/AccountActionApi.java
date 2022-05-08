package com.example.server_capstone.controller;
import com.example.server_capstone.dto.request.AccountRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.AccountResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api")
public interface AccountActionApi {
    @GetMapping(value = "/account/all")
    List<AccountResponse> getAllAccount();

    @PostMapping(value = "/account/check")
    AccountResponse checkAccount(@RequestBody AccountRequest request);

    @PostMapping(value = "/account/regis")
    AccountResponse regisAccount(@RequestBody AccountRequest request);

    @PostMapping(value = "/account/delete")
    GeneralResponse deleteAccount(@RequestBody AccountRequest request);

    @GetMapping(value = "/account/update")
    GeneralResponse updateAccount(@RequestBody AccountRequest request);

    @PostMapping(value = "/account/get")
    AccountResponse getAccount(@RequestBody AccountRequest request);

    @PostMapping(value = "/account/ok")
    GeneralResponse regisAccount(@RequestBody String check);
}
