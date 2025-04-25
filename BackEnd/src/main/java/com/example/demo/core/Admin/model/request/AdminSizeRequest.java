package com.example.demo.core.Admin.model.request;

import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.entity.Size;
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
public class AdminSizeRequest implements DtoToEntity<Size> {

    private Integer id;

    @NotBlank(message = "không bỏ trống tên")
    private String ten;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private String trangThai;

    private String moTa;

    @Override
    public Size dtoToEntity(Size size) {
        size.setMoTa(this.moTa);
        size.setMa(this.ma);
        size.setTen(this.ten);
        size.setNgayTao(DatetimeUtil.getCurrentDate());
        size.setTrangThai(Integer.parseInt(this.trangThai));
        return size;
    }

}
