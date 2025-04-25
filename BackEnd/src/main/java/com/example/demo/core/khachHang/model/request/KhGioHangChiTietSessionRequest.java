package com.example.demo.core.khachHang.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KhGioHangChiTietSessionRequest {

   private Integer idGHCT;


    Integer idCTSP;


    Integer idSP;


    String tenSP;


    String anhMau;


    String anh;


    String tenMauSac;


    String tenSize;


    BigDecimal giaBan;


    BigDecimal giaSPSauGiam;


    Integer soLuong;

    Integer soLuongTon;
}
