package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.service.KHDiaChiService;
import com.example.demo.entity.DiaChi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/khach-hang/dia-chi")
public class KHDiaChiController {

    @Autowired
    KHDiaChiService khDiaChiService;

    @GetMapping("/{userId}")
    public DiaChi getDiaChiByUser(@PathVariable("userId") Integer userId){
        return khDiaChiService.findByUserID(userId);
    }

    @GetMapping("/thanh-toan/{userId}")
    public DiaChi getDiaChiByUseranhTrangThai(@PathVariable("userId") Integer userId){
        return khDiaChiService.findByUserIDAnhTrangThai(userId);
    }
}
