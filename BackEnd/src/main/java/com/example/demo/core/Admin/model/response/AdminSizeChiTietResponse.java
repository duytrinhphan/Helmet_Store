package com.example.demo.core.Admin.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface AdminSizeChiTietResponse {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.ten}")
    String getTen();
}
