package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.model.request.RegisterPayload;
import com.example.demo.core.khachHang.service.RegisterService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khach-hang")
public class KHRegisterController {

    @Autowired
    RegisterService registerService;


    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterPayload payload){

        return registerService.registerUser(payload);

    }
}
