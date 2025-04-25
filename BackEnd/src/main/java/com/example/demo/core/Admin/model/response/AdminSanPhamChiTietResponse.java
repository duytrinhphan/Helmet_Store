package com.example.demo.core.Admin.model.response;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;

public interface AdminSanPhamChiTietResponse {

    Integer getStt();

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.soLuongSanPham}")
    Integer getSoLuongSanPham();

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

    @Value("#{target.vatLieu}")
    String getVatLieu();

    @Value("#{target.trongLuong}")
    String getTrongLuong();

    @Value("#{target.anh}")
    String getAnh();

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
