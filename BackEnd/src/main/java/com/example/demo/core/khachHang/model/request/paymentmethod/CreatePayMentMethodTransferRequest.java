package com.example.demo.core.khachHang.model.request.paymentmethod;

import com.example.demo.core.khachHang.model.request.hoadonchitiet.KHHoaDonChiTietRequest;
import lombok.Data;

import java.util.List;

@Data
public class CreatePayMentMethodTransferRequest {
    public String vnp_Ammount ;
    public String vnp_OrderInfo = "Thanh toan hoa don";
    public String vnp_OrderType = "Thanh toan hoa don";
    public String vnp_TxnRef;
   private List<KHHoaDonChiTietRequest> hdct;
}
