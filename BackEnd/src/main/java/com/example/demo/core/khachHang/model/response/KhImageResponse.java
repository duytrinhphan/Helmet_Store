package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface KhImageResponse {
    @Value("#{target.anh}")
    String getAnh();
}
