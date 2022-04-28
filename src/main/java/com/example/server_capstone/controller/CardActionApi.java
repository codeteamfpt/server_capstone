package com.example.server_capstone.controller;

import com.example.server_capstone.dto.request.CardRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.CardResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
public interface CardActionApi {
    @GetMapping(value = "/card/getall")
    List<CardResponse> getcards(@RequestBody GetAllRequest request);

    @GetMapping(value = "/card/add")
    GeneralResponse addcard(@RequestBody CardRequest request);

    @GetMapping(value = "/card/delete")
    GeneralResponse deletecard(@RequestBody CardRequest request);

    @GetMapping(value = "/card/update")
    GeneralResponse updatecard(@RequestBody CardRequest request);

    @GetMapping(value = "/card/create")
    GeneralResponse createcard(@RequestBody CardRequest request);
}
