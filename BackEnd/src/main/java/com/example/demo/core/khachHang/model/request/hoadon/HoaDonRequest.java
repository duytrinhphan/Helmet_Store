package com.example.demo.core.khachHang.model.request.hoadon;

import com.example.demo.core.khachHang.model.request.hoadonchitiet.KHHoaDonChiTietRequest;
import com.example.demo.core.khachHang.model.response.PaymentVNPay.PayMentVnpayResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HoaDonRequest {

    private BigDecimal tongTien;

    private BigDecimal tienShip;

    private BigDecimal tienSauGiam;

    private Integer idUser;

    private Integer idVoucher;

    private Integer idPayMethod;

    private List<KHHoaDonChiTietRequest> listHDCT;

    private PayMentVnpayResponse responsePayment;
}
