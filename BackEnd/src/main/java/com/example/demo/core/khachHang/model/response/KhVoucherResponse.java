package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface KhVoucherResponse {
    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.giamToiDa}")
    Integer getGiamToiDa();

    @Value("#{target.giaTriGiam}")
    Integer getGiaTriGiam();

    @Value("#{target.moTa}")
    String getMoTa();

    @Value("#{target.soLuong}")
    Integer getSoLuong();



    @Value("#{target.thoiGianBatDau}")
    String getThoiGianBatDau();

    @Value("#{target.thoiGianKetThuc}")
    String getThoiGianKetThuc();

    @Value("#{target.trangThai}")
    Integer getTrangThai();

    @Value("#{target.ten}")
    String getTen();




}
