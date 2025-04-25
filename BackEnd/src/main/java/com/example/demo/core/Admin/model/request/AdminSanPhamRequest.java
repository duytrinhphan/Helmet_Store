package com.example.demo.core.Admin.model.request;

import com.example.demo.entity.Loai;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.ThuongHieu;
import com.example.demo.entity.VatLieu;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.util.DatetimeUtil;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Setter
public class AdminSanPhamRequest implements DtoToEntity<SanPham> {

    private Integer id;

    private String ten;

    private String moTa;

    private String demLot;

    private String quaiDeo;

    private Integer thuongHieu;

    private Integer trangThai;

    private Integer vatLieu;

    private Integer loai;

    private String anh;

    @Override
    public SanPham dtoToEntity(SanPham sanPham) {
        sanPham.setTen(this.getTen());
        sanPham.setNgayTao(DatetimeUtil.getCurrentDate());
        sanPham.setTrangThai(3);
        sanPham.setMoTa(this.getMoTa());
        sanPham.setDemLot(this.getDemLot());
        sanPham.setQuaiDeo(this.getQuaiDeo());
        sanPham.setAnh(this.getAnh());
        sanPham.setVatLieu(VatLieu.builder().id(this.getVatLieu()).build());
        sanPham.setThuongHieu(ThuongHieu.builder().id(this.getThuongHieu()).build());
        sanPham.setLoai(Loai.builder().id(this.getLoai()).build());
        return sanPham;
    }
}
