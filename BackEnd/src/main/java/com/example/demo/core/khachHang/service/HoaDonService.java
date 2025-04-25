package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.request.hoadon.HoaDonRequest;
import com.example.demo.core.khachHang.model.response.KHHoaDonResponse;
import com.example.demo.entity.HoaDon;

public interface HoaDonService {
    HoaDon createHoaDon(HoaDonRequest hoaDonRequest);
}
