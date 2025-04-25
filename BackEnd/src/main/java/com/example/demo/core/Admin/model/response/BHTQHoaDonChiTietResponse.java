package com.example.demo.core.Admin.model.response;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.SanPhamChiTiet;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BHTQHoaDonChiTietResponse {
    private Integer id;
    private String ma;
    private BigDecimal donGia;
    private BigDecimal chietKhau;
    private Integer soLuong;
    private String ngaySua;
    private String ngayTao;
    private Integer trangThai;
    private String lyDo;
    private SanPhamChiTiet sanPhamChiTiet;
    private HoaDon hoaDon;
}
