package com.example.demo.core.Admin.model.request;

import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.entity.MauSac;
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
public class AdminMauSacRequest implements DtoToEntity<MauSac> {
    private Integer id;

    @NotBlank(message = "không bỏ trống tên")
    private String ten;
    @NotBlank(message = "không bỏ trống mã")
    private String ma;

    private String ngaySua;

    private String ngayTao;

    private String moTa;

    @NotBlank(message = "không bỏ trống trạng thái")
    private String trangThai;

    @Override
    public MauSac dtoToEntity(MauSac mauSac) {
        mauSac.setMa(this.ma);
        mauSac.setTen(this.ten);
        mauSac.setMoTa(this.moTa);
        mauSac.setNgayTao(DatetimeUtil.getCurrentDate());
        mauSac.setTrangThai(Integer.parseInt(this.trangThai));
        return mauSac;
    }
}
