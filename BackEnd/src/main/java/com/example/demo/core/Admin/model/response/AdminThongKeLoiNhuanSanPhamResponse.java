package com.example.demo.core.Admin.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface AdminThongKeLoiNhuanSanPhamResponse {

    @Value("#{target.ma}")
    String getMa();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.giaBan}")
    Integer getGiaBan();

    @Value("#{target.giaNhap}")
    Integer getGiaNhap();

    @Value("#{target.anh}")
    String getAnh();

    @Value("#{target.trangThai}")
    Integer getTrangThai();

    @Value("#{target.loiNhuan}")
    Integer getLoiNhuan();
}
