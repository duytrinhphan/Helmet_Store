package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.model.request.KHUserRequest;
import com.example.demo.core.khachHang.service.ThongTinService;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@RestController
@RequestMapping("/api/khach-hang/thong-tin-ca-nhan")
@CrossOrigin(origins = {"*"})
public class ThongTinCaNhanApi {

    @Autowired
    private ThongTinService khUserService;

    @GetMapping("/find-all")
    public ResponseEntity<?> getAll(@RequestParam("token") String token) {
        return ResponseEntity.ok(khUserService.getAll(token));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody KHUserRequest request, @PathVariable Integer id) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
        return ResponseEntity.ok(khUserService.update(request, id));
    }
}
