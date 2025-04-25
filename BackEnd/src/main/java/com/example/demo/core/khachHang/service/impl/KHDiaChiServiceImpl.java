package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.response.DiaChiResponse;
import com.example.demo.core.khachHang.repository.KHDiaChiRepository;
import com.example.demo.core.khachHang.service.KHDiaChiService;
import com.example.demo.entity.DiaChi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KHDiaChiServiceImpl implements KHDiaChiService {
    @Autowired
    KHDiaChiRepository khDiaChiRepo;

    @Override
    public DiaChi findByUserID(Integer id) {
        return khDiaChiRepo.findAllByUserId(id);

    }
    public DiaChi findByUserIDAnhTrangThai(Integer id) {
        return khDiaChiRepo.findDiaChiByIdUserAndTrangThai(id);
    }
}
