package com.example.demo.core.khachHang.model.response;

import com.example.demo.core.khachHang.model.request.KhGioHangChiTietSessionRequest;
import com.example.demo.entity.GioHangChiTiet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KhCartBO {
    List<KhGioHangChiTietSessionRequest> lstCart;
}
