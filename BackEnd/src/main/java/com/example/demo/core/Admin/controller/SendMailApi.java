package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminUserRequest;
import com.example.demo.core.Admin.model.request.OTPResquest;
import com.example.demo.core.Admin.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/mail")
public class SendMailApi {
    @Autowired
    private ClientService clientService;

    @PostMapping(value = "send")
    public ResponseEntity<Boolean> create(@RequestBody AdminUserRequest adminUserRequest) {
        Boolean result = clientService.create(adminUserRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "sendOTP")
    public ResponseEntity<Boolean> sendMailOTP(@RequestBody OTPResquest otpResquest) {
        Boolean result = clientService.createOTP(otpResquest);
        return ResponseEntity.ok(result);
    }
}
