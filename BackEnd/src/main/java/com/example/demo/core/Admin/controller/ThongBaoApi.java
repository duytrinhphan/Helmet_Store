package com.example.demo.core.Admin.controller;


import com.example.demo.core.Admin.service.impl.AdThongBaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/thong-bao")
public class ThongBaoApi {
    @Autowired
    private AdThongBaoServiceImpl adThongBaoService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(adThongBaoService.getAll());
    }

    @GetMapping("/dem")
    public ResponseEntity<?> dem() {
        return ResponseEntity.ok(adThongBaoService.dem());
    }

    @PutMapping("/da-xem/{id}")
    public ResponseEntity<?> daXem(@PathVariable Integer id) {
        return ResponseEntity.ok(adThongBaoService.daXem(id));
    }
}
