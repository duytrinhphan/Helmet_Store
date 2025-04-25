package com.example.demo.core.khachHang.service;

import com.example.demo.entity.DiaChi;

public interface KHDiaChiService {

    DiaChi findByUserID(Integer id);

    DiaChi findByUserIDAnhTrangThai(Integer id);
}
