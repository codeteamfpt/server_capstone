package com.example.server_capstone.controller.imlp;

import com.example.server_capstone.controller.CardActionApi;
import com.example.server_capstone.dto.request.CardRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.CardResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import com.example.server_capstone.service.CardActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardActionImpl implements CardActionApi {
    @Autowired
    CardActionService cardActionService;

    @Override
    public ListResponse<CardResponse> getcards(GetAllRequest request) {
        return cardActionService.getCard(request);
    }
    @Override
    public GeneralResponse addcard(CardRequest request) {
        return cardActionService.addCard(request);
    }

    @Override
    public GeneralResponse deletecard(CardRequest request) {
        return cardActionService.deleteCard(request);
    }

    @Override
    public GeneralResponse updatecard(CardRequest request) {
        return cardActionService.updateCard(request);
    }

    @Override
    public GeneralResponse createcard(CardRequest request) {
        return cardActionService.createCard(request);
    }
}
