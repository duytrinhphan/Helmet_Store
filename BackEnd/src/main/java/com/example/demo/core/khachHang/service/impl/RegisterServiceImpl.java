package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.request.RegisterPayload;
import com.example.demo.core.khachHang.repository.KHUserRepository;
import com.example.demo.core.khachHang.service.RegisterService;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    KHUserRepository khUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(RegisterPayload payload) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        if (khUserRepo.existsByEmail(payload.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        User user = new User();
        user.setMa("U"+randomNumber);
        user.setEmail(payload.getEmail());
        user.setTen(payload.getTen());
        user.setSdt(payload.getSdt());
        user.setRole(Role.USER);
        user.setNgayTao(LocalDateTime.now());
        user.setUserName(payload.getEmail());
        user.setNgaySinh(dateFormat.format(payload.getNgaySinh()));
        user.setGioiTinh(payload.getGioiTinh());
        user.setPassword(passwordEncoder.encode(payload.getMatKhau()));
        return khUserRepo.save(user) ;
    }
}
