package com.example.demo.core.Admin.model.request;

import com.example.demo.entity.KhuyenMai;
import com.example.demo.entity.TrongLuong;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminKhuyenMaiRequest implements DtoToEntity<KhuyenMai> {

    private String ten;

    private String moTa;

    private LocalDateTime thoiGianBatDau;

    private LocalDateTime thoiGianKetThuc;

    private Integer trangThai;

    private Integer giaTriGiam;

    @Override
    public KhuyenMai dtoToEntity(KhuyenMai khuyenmai) {

//        String pattern = "yyyy-MM-dd HH:mm:ss";
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//        LocalDateTime thoiGianBatDau = LocalDateTime.parse(this.thoiGianBatDau, formatter);
//        LocalDateTime thoiGianKetThuc = LocalDateTime.parse(this.thoiGianKetThuc, formatter);

//        khuyenmai.setMa(this.ma);
        khuyenmai.setTen(this.ten);
        khuyenmai.setThoiGianBatDau(this.thoiGianBatDau);
        khuyenmai.setThoiGianKetThuc(this.thoiGianKetThuc);
        khuyenmai.setMoTa(this.moTa);
        khuyenmai.setGiaTriGiam(this.giaTriGiam);
        khuyenmai.setTrangThai(this.trangThai);
        return khuyenmai;
    }
}
