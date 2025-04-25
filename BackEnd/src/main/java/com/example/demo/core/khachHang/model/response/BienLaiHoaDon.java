package com.example.demo.core.khachHang.model.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BienLaiHoaDon {

    private String sdt;
    private String diaChi;
    private String ten;
    private String ma;
    private String giamgia;
    private String tongTien;
    private String ghiChu;
    private String phiShip;

    private String tongThanhToan;
    private LocalDateTime date;
    private Integer soLuong;
    private List<HDCTRespon> items;
}
