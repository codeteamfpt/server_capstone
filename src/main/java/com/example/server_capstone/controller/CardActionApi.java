package com.example.server_capstone.controller;

import com.example.server_capstone.dto.request.CardRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.CardResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface CardActionApi {
    @GetMapping(value = "/card/getall")
    ListResponse<CardResponse> getcards(GetAllRequest request);

    @GetMapping(value = "/card/add")
    GeneralResponse addcard(CardRequest request);

    @GetMapping(value = "/card/delete")
    GeneralResponse deletecard(CardRequest request);

    @GetMapping(value = "/card/update")
    GeneralResponse updatecard(CardRequest request);

    @GetMapping(value = "/card/create")
    GeneralResponse createcard(CardRequest request);
}
