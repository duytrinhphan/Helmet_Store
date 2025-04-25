package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.model.request.hoadon.HoaDonRequest;
import com.example.demo.core.khachHang.model.request.paymentmethod.CreatePayMentMethodTransferRequest;
import com.example.demo.core.khachHang.service.HoaDonService;
import com.example.demo.core.khachHang.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api")
public class PaymentController {
    @Value("${frontend.base-endpoint}")
    private  String BASE_FRONTEND_ENDPOINT;

    @Autowired
    PaymentService paymentService;

    @Autowired
    HoaDonService hoaDonService;
    @PostMapping("/payment-vnpay")
    public String pay(@RequestBody CreatePayMentMethodTransferRequest payModel, HttpServletRequest request){
        try {
            return paymentService.payWithVNPAYOnline(payModel, request) ;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/payment-callback")
    public ResponseEntity<Boolean> paymentCallback(@RequestParam Map<String, String> queryParams, HttpServletResponse response) throws IOException {
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
        if ("00".equals(vnp_ResponseCode)) {
            response.sendRedirect(BASE_FRONTEND_ENDPOINT + "/gio-hang/thanh-toan/thanh-cong");
            return ResponseEntity.ok(true);
        } else{
            response.sendRedirect(BASE_FRONTEND_ENDPOINT + "/gio-hang/thanh-toan/that-bai");
        }



        return ResponseEntity.ok(false);
    }
}
