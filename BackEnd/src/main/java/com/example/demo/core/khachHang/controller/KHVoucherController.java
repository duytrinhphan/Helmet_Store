package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.model.response.KhVoucherResponse;
import com.example.demo.core.khachHang.model.response.VoucherResponse;
import com.example.demo.core.khachHang.service.KHVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khach-hang/voucher")
public class KHVoucherController {

    @Autowired
    KHVoucherService khVoucherService;

    @GetMapping("/{id}")
    public List<VoucherResponse> getListVoucher(@PathVariable  Integer id){
        return khVoucherService.listVoucher(id);
    }


}
