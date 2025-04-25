package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface KhMauSacResponse {
    @Value("#{target.idMauSac}")
    Integer getId();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.anh}")
    String getAnh();
}
