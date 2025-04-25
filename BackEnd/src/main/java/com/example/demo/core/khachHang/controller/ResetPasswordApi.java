package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.service.ResetPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khach-hang/reset-password")
public class ResetPasswordApi {
    private final ResetPasswordService resetPasswordService;

    public ResetPasswordApi(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @GetMapping
    public ResponseEntity<?> handleForgotPassword(@RequestParam("email") String email) {
        return resetPasswordService.handleForgotPassword(email) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email không tồn tại trong hệ thống!");
    }

    @PostMapping
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email,
                                           @RequestParam("token") String token,
                                           @RequestHeader(required = false) String password) {
        return resetPasswordService.resetPassword(token, email, password) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
