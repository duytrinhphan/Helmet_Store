package com.example.demo.core.khachHang.service;

public interface ResetPasswordService {
    boolean handleForgotPassword(String email);
    void sendResetPasswordMail(String email, String ten, String token);
    boolean resetPassword(String token, String email, String password);
}
