package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.request.hoadon.HoaDonRequest;
import com.example.demo.core.khachHang.model.response.KHHoaDonResponse;

import java.util.List;

public interface KHHoaDonService {

    List<KHHoaDonResponse> getAll(String token);

    List<KHHoaDonResponse> getHoaDonTrangThai(String token, Integer trangThai,Integer trangThai2,Integer trangThai3,Integer trangThai4);

    KHHoaDonResponse huyHoaDonChoXacNhan(Integer idHD, String lyDo);

    KHHoaDonResponse findById(Integer idHD);

    KHHoaDonResponse findByMaHD(String maHD);
}
