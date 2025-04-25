package com.example.demo.core.Admin.model.response;

import com.example.demo.entity.Image;
import com.example.demo.entity.SanPhamChiTiet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamDOT {

    private List<AdminImageResponse> img;
    private List<AdminSanPhamChiTiet2Response> sanPhamChiTiet;
    private  Integer id;
    private String ten;
    private String moTa;
    private String ma;
    private Integer trangThai;
    private String ngayTao;
    private Integer soLuongTon;
    private String quaiDeo;
    private String demLot;
    private String vatLieu;
    private String loai;
    private String thuongHieu;
    private String anh;
    private String ngaySua;


}
