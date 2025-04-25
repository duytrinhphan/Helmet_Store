package com.example.demo.core.khachHang.model.request.hoadonchitiet;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KHHoaDonChiTietRequest {

    private Integer idCTSP;

    private BigDecimal donGia;

    private Integer soLuong;

    private  Integer sanPhamChiTiet;



}
