package com.example.demo.core.Admin.model.request;

import com.example.demo.entity.SanPham;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreatExcelSanPhamRequest {

    private String SanPham;

    private String demLot;

    private String quaiDeo;

    private String thuongHieu;

    private String loai;

    private String trangThai;

    private String moTa;

    private String soLuongTon;

    private String giaBan;

    private String giaNhap;

    private String khuyenMai;

    private String vatLieu;

    private String moTaMauSacChiTiet;

    private String trongLuong;

    private List<String> soLuongSize;

    private List<String> idMauSac;

    private List<String> idSize;

    private List<String> imgMauSac;

    private List<String> imagesProduct;

}
