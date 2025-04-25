package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface VoucherResponse {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.moTa}")
    String getMoTa();

    @Value("#{target.giamToiDa}")
    Integer getGiamToiDa();

    @Value("#{target.giaTriGiam}")
    Integer getGiaTriGiam();


    @Value("#{target.soLuong}")
    Integer getSoLuong();

    @Value("#{target.thoiGianKetThuc}")
    String getThoiGianKetThuc();



}
