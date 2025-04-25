package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.model.response.AdminHoaDonChitietResponse;
import com.example.demo.core.khachHang.service.KHHoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/khach-hang/hoa-don-chi-tiet")
public class KHHoaDonChiTietApi {

    @Autowired
    private KHHoaDonChiTietService hdctService;

    @GetMapping("/find-by-id-hd/{id}")
    public ResponseEntity<?> findByIdHd(@PathVariable Integer id) {
        return ResponseEntity.ok(hdctService.findHDCTByIdHoaDon(id));
    }

    @GetMapping("/find-by-ma-hd/{ma}")
    public ResponseEntity<?> findByMaHd(@PathVariable String ma) {
        return ResponseEntity.ok(hdctService.findHDCTByMaHoaDon(ma));
    }

    @GetMapping("/find-by-id-hd-tra")
    public ResponseEntity<?> findByIdHdTra(@RequestParam String token) {
        return ResponseEntity.ok(hdctService.findHDCTByIdHoaDonTraHang(token));
    }

}