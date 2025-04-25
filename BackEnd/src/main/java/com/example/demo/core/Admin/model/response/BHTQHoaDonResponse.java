package com.example.demo.core.Admin.model.response;

import com.example.demo.entity.DiaChi;
import com.example.demo.entity.PhuongThucThanhToan;
import com.example.demo.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BHTQHoaDonResponse {
    private Integer id;
    private String ma;
    private BigDecimal tongTien;
    private String tenNguoiNhan;
    private LocalDateTime ngayNhan;
    private BigDecimal tienShip;
    private BigDecimal tienSauKhiGiam;
    private BigDecimal tienKhachDua;
    private String lyDo;
    private LocalDateTime ngaySua;
    private LocalDateTime ngayTao;
    private Integer trangThai;
    private LocalDateTime ngayThanhToan;
    private LocalDateTime ngayShip;
    private Integer hinhThucGiaoHang;
    private String moTa;
    private DiaChi diaChi;
    private User user;
    private User nguoiTao;
    private PhuongThucThanhToan phuongThucThanhToan;
}