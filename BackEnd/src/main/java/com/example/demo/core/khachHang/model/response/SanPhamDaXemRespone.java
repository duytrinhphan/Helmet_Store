package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface SanPhamDaXemRespone {

    @Value("#{target.id}")
    Integer getIdSP();

    @Value("#{target.ma}")
    String getMa();

    @Value("#{target.ten}")
    String getTenSP();

    @Value("#{target.quaiDeo}")
    String getQuaiDeo();

    @Value("#{target.demLot}")
    String getDemLot();

    @Value("#{target.anh}")
    String getAnh();

    @Value("#{target.moTa}")
    String getMoTa();

    @Value("#{target.trangThai}")
    Integer getTrangThai();

    @Value("#{target.giaBanMax}")
    String getGiaBanMax();

    @Value("#{target.giaBanMin}")
    String getGiaBanMin();

    @Value("#{target.giaSauGiamMax}")
    String getGiaSauGiamMax();

    @Value("#{target.giaSauGiamMin}")
    String getGiaSauGiamMin();

    @Value("#{target.tenLoai}")
    String getTenLoai();

    @Value("#{target.ngayTao}")
    String getNgayTao();

    @Value("#{target.tenThuongHieu}")
    String getTenThuongHieu();

}
