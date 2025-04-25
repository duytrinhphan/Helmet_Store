package com.example.demo.core.Admin.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface AdminThongKeLoiNhuanHoaDonResponse {
    @Value("#{target.idHD}")
    Integer getIdHD();

    @Value("#{target.maHD}")
    String getMaHD();

    @Value("#{target.ngayTao}")
    String getNgayTao();

    @Value("#{target.tenPhuongThucThanhToan}")
    String getTenPhuongThucThanhToan();

    @Value("#{target.tienSauGiam}")
    Integer getTienSauGiam();

    @Value("#{target.tongTien}")
    Integer getTongTien();

    @Value("#{target.trangThai}")
    Integer getTrangThai();

    @Value("#{target.loiNhuan}")
    Integer getLoiNhuan();

    @Value("#{target.tienShip}")
    Integer getTienShip();

    @Value("#{target.diaChiCuThe}")
    String getDiaChiCuThe();

    @Value("#{target.idTinhThanh}")
    String getIdTinhThanh();

    @Value("#{target.tenTinhThanh}")
    String getTenTinhThanh();

    @Value("#{target.idQuanHuyen}")
    String getIdQuanHuyen();

    @Value("#{target.tenQuanHuyen}")
    String getTenQuanHuyen();

    @Value("#{target.idPhuongXa}")
    String getIdPhuongXa();

    @Value("#{target.tenPhuongXa}")
    String getTenPhuongXa();

    @Value("#{target.hoanTien}")
    Integer getHoanTien();

}
