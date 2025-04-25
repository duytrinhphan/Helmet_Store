package com.example.demo.core.Admin.model.response;

import org.springframework.beans.factory.annotation.Value;


public interface AdminThongKeLoaiResponse {
    @Value("#{target.ma}")
    String getMa();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.tongTien}")
    Integer getTongTien();
}
