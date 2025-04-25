package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface KhSizeResponse {
    @Value("#{target.idSize}")
    Integer getId();

    @Value("#{target.ten}")
    String getTen();
}
