package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.response.SanPhamResponse;
import com.example.demo.core.khachHang.repository.KHSanPhamRepository;
import com.example.demo.core.khachHang.service.SanPhamDaXemService;
import com.example.demo.entity.SanPham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SanPhamDaXemServiceImpl implements SanPhamDaXemService {

    @Autowired
    KHSanPhamRepository khSanPhamRepository;

    @Override
    public SanPhamResponse addSPLenSession(Integer id) {
        // lấy ctsp từ repo
        SanPhamResponse sanPham = khSanPhamRepository.findSPByIdSP(id);
        return sanPham;
    }
}
