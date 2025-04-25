package com.example.demo.core.Admin.model.request;

import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.entity.Loai;
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
public class AdminLoaiRequest implements DtoToEntity<Loai> {

    private Integer id;

    @NotBlank(message = "Không bỏ trống tên")
    private String ten;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private Integer trangThai;


    @Override
    public Loai dtoToEntity(Loai loai) {
        loai.setMa(this.ma);
        loai.setNgayTao(DatetimeUtil.getCurrentDate());
        loai.setTen(this.ten);
        loai.setTrangThai(this.trangThai);
        return loai;
    }
}
