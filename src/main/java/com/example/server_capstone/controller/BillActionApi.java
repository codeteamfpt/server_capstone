package com.example.server_capstone.controller;

import com.example.server_capstone.dto.request.BillRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.BillResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
public interface BillActionApi {
    @PostMapping(value = "/bill/save")
    GeneralResponse billSave(@RequestBody BillRequest request);

    @PostMapping(value = "/bill/get")
    List<BillResponse> billSave(@RequestBody GetAllRequest request);
}
