package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface KHUserResponse {

    Integer getStt();

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.anh}")
    String getAnh();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.ngaySinh}")
    String getNgaySinh();

    @Value("#{target.gioiTinh}")
    Integer getGioiTinh();

    @Value("#{target.ma}")
    String getMa();

    @Value("#{target.pass}")
    String getPass();

    @Value("#{target.role}")
    String getRole();

    @Value("#{target.sdt}")
    String getSdt();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.trangThai}")
    Integer getTrangThai();

    @Value("#{target.userName}")
    String getUserName();

    @Value("#{target.soLuongHoaDon}")
    Integer getSoLuongHoaDon();
}
