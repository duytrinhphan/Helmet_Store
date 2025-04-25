package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface DetailSanPhamResponse {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.tenSP}")
    String getTenSP();

    @Value("#{target.maSP}")
    String getMaSP();

    @Value("#{target.thuongHieu}")
    String getThuongHieu();

    @Value("#{target.vatLieu}")
    String getVatLieu();

    @Value("#{target.loai}")
    String getLoai();

    @Value("#{target.trongLuong}")
    String getTrongLuong();

    @Value("#{target.tenKM}")
    String getTenKM();

    @Value("#{target.giaBan}")
    BigDecimal getGiaBan();

    @Value("#{target.giaSauGiam}")
    BigDecimal getGiaSauGiam();


    @Value("#{target.quaiDeo}")
    String getQuaiDeo();

    @Value("#{target.demLot}")
    String getDemLot();
}
