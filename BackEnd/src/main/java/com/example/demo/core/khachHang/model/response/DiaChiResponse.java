package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface DiaChiResponse {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.diaChi}")
    String getDiaChi();

    @Value("#{target.idQuanHuyen}")
    Integer getIdQuanHuyen();

    @Value("#{target.tenQuanHuyen}")
    String getTenQuanHuyen();

    @Value("#{target.idTinhThanh}")
    Integer getIdTinhThanh();

    @Value("#{target.idphuongXa}")
    String getIdphuongXa();

    @Value("#{target.tenTinhThanh}")
    String getTenTinhThanh();

    @Value("#{target.tenphuongXa}")
    String getTenphuongXa();

    @Value("#{target.loaiDiaChi}")
    String getLoaiDiaChi();

    @Value("#{target.ngaySua}")
    String getNgaySua();

    @Value("#{target.ngayTao}")
    String getNgayTao();

    @Value("#{target.trangThai}")
    Integer getTrangThai();
}
