package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.service.KHSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khach-hang/san-pham")
@CrossOrigin(origins = {"*"})
public class KHSanPhamApi {

    @Autowired
    private KHSanPhamService service;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

}
