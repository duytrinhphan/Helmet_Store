package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.model.request.hoadon.HoaDonRequest;
import com.example.demo.core.khachHang.service.HoaDonService;
import com.example.demo.entity.HoaDon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khach-hang/checkout")
public class HoaDonController {

    @Autowired
    HoaDonService hoaDonService;

    @PostMapping()
    public HoaDon create(@RequestBody HoaDonRequest request)  {
        return hoaDonService.createHoaDon(request);
    }


}
