package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface SizeResponse {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.moTa}")
    String getMoTa();

    @Value("#{target.soLuong}")
    Integer getSoLuong();

}
