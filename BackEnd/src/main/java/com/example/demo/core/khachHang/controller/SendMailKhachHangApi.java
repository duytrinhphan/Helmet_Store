package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.model.request.OTPResquest;
import com.example.demo.core.Admin.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khach-hang/mail")
public class SendMailKhachHangApi {

    @Autowired
    private ClientService clientService;

    @PostMapping(value =
            "/sendOTP")
    public ResponseEntity<Boolean> sendMailOTP(@RequestBody OTPResquest otpResquest) {
        Boolean result = clientService.createOTP(otpResquest);
        return ResponseEntity.ok(result);
    }
}
