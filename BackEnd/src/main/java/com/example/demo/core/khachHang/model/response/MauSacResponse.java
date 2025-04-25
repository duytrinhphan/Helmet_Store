package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface MauSacResponse {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.anh}")
    String getAnh();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.moTa}")
    String getMoTa();

    @Value("#{target.soLuong}")
    Integer getSoLuong();

    @Value("#{target.idMS}")
    Integer getIdMS();
}
