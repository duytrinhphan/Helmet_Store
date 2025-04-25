package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface SelectedSanPhamResponse {

    @Value("#{target.IdMS}")
    Integer getIdMS();

    @Value("#{target.IdSize}")
    Integer getIdSize();

    @Value("#{target.anh}")
    String getAnh();

    @Value("#{target.tenSP}")
    String getTenSP();

    @Value("#{target.giaBan}")
    BigDecimal getGiaBan();

    @Value("#{target.giaSauGiam}")
    BigDecimal getGiaSauGiam();


    @Value("#{target.tenSize}")
    String getTenSize();

    @Value("#{target.SLMS}")
    Integer getSLMS();

}
