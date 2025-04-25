package com.example.demo.core.Admin.model.request;

import com.example.demo.entity.VatLieu;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.util.DatetimeUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminVatLieuRequest implements DtoToEntity<VatLieu> {

    private Integer id;

    @NotBlank(message = "Không bỏ trống tên")
    private String ten;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private Integer trangThai;

    private String moTa;

    @Override
    public VatLieu dtoToEntity(VatLieu e) {
        e.setMa(this.ma);
        e.setNgayTao(DatetimeUtil.getCurrentDate());
        e.setTen(this.ten);
        e.setTrangThai(this.trangThai);
        e.setMoTa(this.moTa);
        return e;
    }
}
