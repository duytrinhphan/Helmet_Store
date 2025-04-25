package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface KHSanPhamChiTiet2Response {
    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.ma}")
    String getMa();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.giaBan}")
    BigDecimal getGiaBan();

    @Value("#{target.giaNhap}")
    BigDecimal getGiaNhap();

    @Value("#{target.soLuongTon}")
    Integer getSoLuongTon();

    @Value("#{target.trangThai}")
    Integer getTrangThai();

    @Value("#{target.quaiDeo}")
    String getQuaiDeo();

    @Value("#{target.demLot}")
    String getDemLot();

    @Value("#{target.moTa}")
    String getMoTa();

    @Value("#{target.loai}")
    String getLoai();

    @Value("#{target.thuongHieu}")
    String getThuongHieu();

    @Value("#{target.trongLuong}")
    String getTrongLuong();

    @Value("#{target.donVi}")
    String getDonVi();

    @Value("#{target.anh}")
    String getAnh();

    @Value("#{target.tenSize}")
    String getTenSize();

    @Value("#{target.tenMauSac}")
    String getTenMauSac();

    @Value("#{target.tenKM}")
    String getTenKM();

    @Value("#{target.thoiGianBatDau}")
    String getThoiGianBatDau();

    @Value("#{target.thoiGianKetThuc}")
    String getThoiGianKetThuc();

    @Value("#{target.giaSauGiam}")
    Integer getGiaSauGiam();

    @Value("#{target.giaTriGiam}")
    Integer getGiaTriGiam();
}
