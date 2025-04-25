package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.response.KHHoaDonChiTietResponse;
import com.example.demo.core.khachHang.model.response.KhHoaDonTraHangResponse;

import java.util.List;

public interface KHHoaDonChiTietService {

    List<KHHoaDonChiTietResponse> findHDCTByIdHoaDon(Integer idHD);

    List<KHHoaDonChiTietResponse> findHDCTByMaHoaDon(String maHD);

    List<KhHoaDonTraHangResponse> findHDCTByIdHoaDonTraHang(String token);
}