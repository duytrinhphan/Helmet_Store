package com.example.demo.core.khachHang.model.response;


import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface GioHangCTResponse {

    @Value("#{target.idGHCT}")
    Integer getIdGHCT();

    @Value("#{target.idCTSP}")
    Integer getIdCTSP();

    @Value("#{target.idSP}")
    Integer getIdSP();

    @Value("#{target.tenSP}")
    String getTenSP();

    @Value("#{target.anhSpct}")
    String getAnhMau();

    @Value("#{target.anh}")
    String getAnh();

    @Value("#{target.tenMauSac}")
    String getTenMauSac();

    @Value("#{target.tenSize}")
    String getTenSize();

    @Value("#{target.giaBan}")
    BigDecimal getGiaBan();

    @Value("#{target.giaSPSauGiam}")
    BigDecimal getGiaSPSauGiam();

    @Value("#{target.soLuong}")
    Integer getSoLuong();

    @Value("#{target.soLuongTon}")
    Integer getSoLuongTon();

    @Value("#{target.trangThaiSPCT}")
    Integer getTrangThaiSPCT();

    @Value("#{target.trangThaiSP}")
    Integer getTrangThaiSP();

}