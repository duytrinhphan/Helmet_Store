package com.example.demo.core.Admin.model.response;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

public interface AdminHoaDonResponse {
    Integer getStt();

    @Value("#{target.idHD}")
    Integer getIdHD();

    @Value("#{target.maHD}")
    String getMaHD();

    @Value("#{target.nguoiTao}")
    String getNguoiTao();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.sdt}")
    String getSdt();

    @Value("#{target.hinhThucGiaoHang}")
    String getHinhThucGiaoHang();

    @Value("#{target.ngayThanhToan}")
    String getNgayThanhToan();

    @Value("#{target.ngayNhan}")
    String getNgayNhan();

    @Value("#{target.ngayShip}")
    String getNgayShip();

    @Value("#{target.ngaySua}")
    String getNgaySua();

    @Value("#{target.ngayTao}")
    String getNgayTao();

    @Value("#{target.tenNguoiNhan}")
    String getTenNguoiNhan();

    @Value("#{target.tienSauKhiGiam}")
    String getTienSauKhiGiam();

    @Value("#{target.tienShip}")
    String getTienShip();

    @Value("#{target.tongTien}")
    String getTongTien();

    @Value("#{target.trangThai}")
    String getTrangThai();

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

    @Value("#{target.lyDo}")
    String getLyDo();

    @Value("#{target.tenPTTT}")
    String getTenPTTT();

    @Value("#{target.moTa}")
    String getMoTa();

    @Value("#{target.idUser}")
    Integer getIdUser();

    @Value("#{target.idVoucher}")
    Integer getIdVoucher();

    @Value("#{target.tenVoucher}")
    String getTenVoucher();


}
