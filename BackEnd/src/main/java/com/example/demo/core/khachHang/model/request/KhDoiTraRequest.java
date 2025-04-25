package com.example.demo.core.khachHang.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KhDoiTraRequest {
    private Integer idHDCT;

    private Integer idUser;

    private Integer idDiaChi;

     private  String tienShip;

     private String lyDo;

     private  Integer soLuong;
}
