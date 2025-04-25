package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.service.AdHoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/hoa-don-chi-tiet")
public class HoaDonChiTietApi {
    @Autowired
    private AdHoaDonChiTietService hdctService;

    @GetMapping("/find-by-id-hd/{id}")
    public ResponseEntity<?> findByIdHd(@PathVariable Integer id) {
        return ResponseEntity.ok(hdctService.findHDCTByIdHoaDon(id));
    }

    @GetMapping("/find-by-trang-thai/{trangThai}")
    public ResponseEntity<?> findByTrangThai(@PathVariable Integer trangThai) {
        return ResponseEntity.ok(hdctService.findHDCTByTrangThai(trangThai));
    }
}
