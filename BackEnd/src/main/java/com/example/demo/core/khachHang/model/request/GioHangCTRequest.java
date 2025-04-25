package com.example.demo.core.khachHang.model.request;

import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.util.DatetimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GioHangCTRequest implements DtoToEntity<GioHangChiTiet> {

    private String ma;

    private Integer soLuong;

    private String ngayTao;

    private String ngaySua;

    private  Integer sanPhamChiTiet;


    @Override
    public GioHangChiTiet dtoToEntity(GioHangChiTiet ghct) {
        ghct.setMa(this.ma);
        ghct.setSoLuong(this.soLuong);
        ghct.setNgayTao(DatetimeUtil.getCurrentDate());
        ghct.setSanPhamChiTiet(SanPhamChiTiet.builder().id(this.getSanPhamChiTiet()).build());
        return ghct;
    }
}
