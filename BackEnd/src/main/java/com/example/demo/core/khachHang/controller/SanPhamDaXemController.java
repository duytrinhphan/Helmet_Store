package com.example.demo.core.khachHang.controller;


import com.example.demo.core.khachHang.service.SanPhamDaXemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khach-hang/san-pham-da-xem")
@CrossOrigin(origins = {"*"})
public class SanPhamDaXemController {
    @Autowired
    private SanPhamDaXemService spDaXemService;

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> getSPById(@PathVariable Integer id){
        return ResponseEntity.ok(spDaXemService.addSPLenSession(id));
    }

}
