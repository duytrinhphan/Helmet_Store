package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface KhHoaDonTraHangResponse {
    Integer getStt();

    @Value("#{target.idHD}")
    Integer getIdHD();

    @Value("#{target.maHD}")
    String getMaHD();

    @Value("#{target.soLuong}")
    Integer getSoLuong();

    @Value("#{target.donGia}")
    Integer getDonGia();

    @Value("#{target.trangThaihdct}")
    Integer getTrangThaiHoaDonChiTiet();
}
