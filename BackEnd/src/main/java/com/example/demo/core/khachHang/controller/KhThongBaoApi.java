package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.service.impl.AdThongBaoServiceImpl;
import com.example.demo.core.khachHang.service.impl.ThongBaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/khach-hang/thong-bao")
public class KhThongBaoApi {
    @Autowired
    private ThongBaoServiceImpl thongBaoService;

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam  String token) {
        return ResponseEntity.ok(thongBaoService.getAll(token));
    }

    @GetMapping("/dem")
    public ResponseEntity<?> dem(@RequestParam  String token) {
        return ResponseEntity.ok(thongBaoService.dem(token));
    }

    @PutMapping("/da-xem/{id}")
    public ResponseEntity<?> daXem(@PathVariable Integer id) {
        return ResponseEntity.ok(thongBaoService.daXem(id));
    }
}
