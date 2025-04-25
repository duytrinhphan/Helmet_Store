package com.example.demo.core.Admin.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface AdminVoucherGetUserResponse {

    Integer getStt();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.ma}")
    String getMa();

    @Value("#{target.sdt}")
    String getSdt();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.tongTien}")
    Integer getTongTien();

}
