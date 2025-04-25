package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface KHSanPhamResponse {
    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.ma}")
    String getMa();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.anh}")
    String getAnh();

    @Value("#{target.moTa}")
    String getMoTa();

    @Value("#{target.trangThai}")
    Integer getTrangThai();

    @Value("#{target.ngayTao}")
    String getNgayTao();

    @Value("#{target.ngaySua}")
    String getNgaySua();

    @Value("#{target.demLot}")
    String getDemLot();

    @Value("#{target.quaiDeo}")
    String getQuaiDeo();

    @Value("#{target.thuongHieu}")
    String getThuongHieu();

    @Value("#{target.vatLieu}")
    String getVatLieu();

    @Value("#{target.loai}")
    String getLoai();
}
