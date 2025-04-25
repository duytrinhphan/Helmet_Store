package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.request.LoginPayLoad;
import com.example.demo.entity.User;

import java.util.Optional;

public interface KHUserService {
    User dangNhapGoogle(String email, String ten, String anh);
    User findByToken(String token);
    Optional<User> findByEmail(String email);
    String checkValiDate(LoginPayLoad loginPayload);
    User createAccountGG(String email, String ten, String anh);
    User updateSDT(Integer id, String sdt);
    boolean doiMatKhau(Integer userId, String oldPassword, String newPassword);
}
