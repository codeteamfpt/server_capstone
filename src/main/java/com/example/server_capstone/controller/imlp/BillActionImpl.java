package com.example.server_capstone.controller.imlp;

import com.example.server_capstone.controller.BillActionApi;
import com.example.server_capstone.dto.request.BillRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.BillResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.service.CartActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BillActionImpl implements BillActionApi {
    @Autowired
    CartActionService cartActionService;

    @Override
    public GeneralResponse billSave(BillRequest request) {
        return cartActionService.billSave(request);
    }

    @Override
    public List<BillResponse> billSave(GetAllRequest request) {
        return cartActionService.getBillByAccount(request);
    }
}
