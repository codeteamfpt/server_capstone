package com.example.server_capstone.controller;

import com.example.server_capstone.dto.request.CartRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.CartResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
public interface CartActionApi {
    @PostMapping(value = "/cart/getall")
    List<CartResponse> getcarts(@RequestBody GetAllRequest request);

    @PostMapping(value = "/cart/pay")
    double paycart(@RequestBody GetAllRequest request);

    @PostMapping(value = "/cart/add")
    GeneralResponse addcart(@RequestBody CartRequest request);

    @PostMapping(value = "/cart/delete")
    GeneralResponse deletecart(@RequestBody CartRequest request);

    @PostMapping(value = "/cart/update")
    GeneralResponse updatecart(@RequestBody CartRequest request);

    @PostMapping(value = "/cart/create")
    GeneralResponse createcart(@RequestBody CartRequest request);
}
