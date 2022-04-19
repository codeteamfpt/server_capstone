package com.example.server_capstone.controller.imlp;

import com.example.server_capstone.controller.CardActionApi;
import com.example.server_capstone.dto.request.CardRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.CardResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardActionImpl implements CardActionApi {
    @Override
    public ListResponse<CardResponse> getcards(GetAllRequest request) {
        return null;
    }

    @Override
    public GeneralResponse addcard(CardRequest request) {
        return null;
    }

    @Override
    public GeneralResponse deletecard(CardRequest request) {
        return null;
    }

    @Override
    public GeneralResponse updatecard(CardRequest request) {
        return null;
    }
}
