package com.example.demo.core.Admin.model.request;


import com.example.demo.entity.Voucher;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminVoucherRequest implements DtoToEntity<Voucher> {

    private Integer id;

    @NotBlank(message = "không bỏ trống tên")
    private String ten;

    private LocalDateTime thoiGianBatDau;

    private LocalDateTime thoiGianKetThuc;

    private String moTa;

    private BigDecimal giamToiDa;

    private Integer trangThai;

    private Integer soLuong;

    private Integer giaTriGiam;

    @Override
    public Voucher dtoToEntity(Voucher voucher) {

        voucher.setTen(this.ten);
        voucher.setThoiGianBatDau(this.thoiGianBatDau);
        voucher.setThoiGianKetThuc(this.thoiGianKetThuc);
        voucher.setMoTa(this.moTa);
        voucher.setGiamToiDa(this.giamToiDa);
        voucher.setTrangThai(this.trangThai);
        voucher.setSoLuong(this.soLuong);
        voucher.setGiaTriGiam(this.giaTriGiam);
        return voucher;
    }
}
