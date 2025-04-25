package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.model.request.KHDiaChiRequest;
import com.example.demo.core.khachHang.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/khach-hang/dia-chi")
@CrossOrigin(origins = {"*"})
public class DiaChiApi {

    @Autowired
    private DiaChiService service;

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getDiaChi(@PathVariable Integer id) {
//        return ResponseEntity.ok(service.getUserByDiaChi(id));
//    }

    @GetMapping("/find-all")
    public ResponseEntity<?> getAll(@RequestParam("token") String token) {
        return ResponseEntity.ok(service.getAll(token));
    }

    @GetMapping("/find-dia-chi-mac-dinh")
    public ResponseEntity<?> findDiaChiByIdUserAndTrangThai(@RequestParam("token") String token) {
        return ResponseEntity.ok(service.findDiaChiByIdUserAndTrangThai(token));
    }

    @PutMapping("{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody KHDiaChiRequest request,@RequestParam String token) {
        return ResponseEntity.ok(service.addDiaChi(request,token));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody KHDiaChiRequest request,@RequestParam String token) {
        return ResponseEntity.ok(service.updateDiaChi(request, id,token));
    }

    @PutMapping("/thiet-lap-mac-dinh/{id}")
    public ResponseEntity<?> thietLapMacDinh(@PathVariable("id") Integer id, @RequestParam String token) {
        return ResponseEntity.ok(service.thietLapMacDinh(id, token));
    }
}
