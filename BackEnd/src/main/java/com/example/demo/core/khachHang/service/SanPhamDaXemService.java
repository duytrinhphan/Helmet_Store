package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.request.KhGioHangChiTietSessionRequest;
import com.example.demo.core.khachHang.model.response.SanPhamResponse;
import com.example.demo.entity.SanPham;

public interface SanPhamDaXemService {

    SanPhamResponse addSPLenSession(Integer id);

}
