package com.example.server_capstone.controller.imlp;

import com.example.server_capstone.controller.CartActionApi;
import com.example.server_capstone.dto.request.CartRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.CartResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.service.CartActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartActionImpl implements CartActionApi {
    @Autowired
    CartActionService cartActionService;

    @Override
    public List<CartResponse> getcarts(GetAllRequest request) {
        return cartActionService.getCart(request);
    }

    @Override
    public double paycart(GetAllRequest request) {
        return cartActionService.payCart(request);
    }

    @Override
    public GeneralResponse addcart(CartRequest request) {
        return cartActionService.addCart(request);
    }

    @Override
    public GeneralResponse deletecart(CartRequest request) {
        return cartActionService.deleteCart(request);
    }

    @Override
    public GeneralResponse updatecart(CartRequest request) {
        return cartActionService.updateCart(request);
    }

    @Override
    public GeneralResponse createcart(CartRequest request) {
        return cartActionService.createCart(request);
    }
}
