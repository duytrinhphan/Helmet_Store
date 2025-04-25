package com.example.demo.core.khachHang.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface KHHoaDonChiTietResponse {
    Integer getStt();

    @Value("#{target.idHD}")
    Integer getIdHD();

    @Value("#{target.maHD}")
    String getMaHD();

    @Value("#{target.maHDCT}")
    String getMaHDCT();

    @Value("#{target.nguoiTao}")
    String getNguoiTao();

    @Value("#{target.maSP}")
    String getMaSP();

    @Value("#{target.tenSP}")
    String getTenSP();

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

    @Value("#{target.giaBan}")
    String getGiaBan();

    @Value("#{target.donGia}")
    String getDonGia();

    @Value("#{target.soLuong}")
    String getSoLuong();

    @Value("#{target.giaSPSauGiam}")
    String getGiaSPSauGiam();

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

    @Value("#{target.mauSac}")
    String getTenMauSac();

    @Value("#{target.size}")
    String getTenSize();

    @Value("#{target.anh}")
    String getAnh();

    @Value("#{target.trongLuong}")
    Integer getTrongLuong();

    @Value("#{target.idDiaChi}")
    Integer getIdDiaChi();

    @Value("#{target.idHoaDonCT}")
    Integer getIdHDCT();

    @Value("#{target.trangThaiHdct}")
    Integer getTrangThaiHDCT();

    @Value("#{target.idUser}")
    Integer getIdUser();

    @Value("#{target.soLuongTon}")
    Integer getSoLuongTon();

    @Value("#{target.idSPCT}")
    Integer getIdSPCT();

    @Value("#{target.idSize}")
    Integer getIdSize();

    @Value("#{target.idMauSac}")
    Integer getIdMau();


}
