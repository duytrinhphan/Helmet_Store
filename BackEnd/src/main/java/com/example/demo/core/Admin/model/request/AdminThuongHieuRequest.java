package com.example.demo.core.Admin.model.request;

import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.entity.ThuongHieu;
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
public class AdminThuongHieuRequest implements DtoToEntity<ThuongHieu> {

    private Integer id;

    @NotBlank(message = "Không bỏ trống tên")
    private String ten;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private Integer trangThai;

    @Override
    public ThuongHieu dtoToEntity(ThuongHieu thuongHieu) {
        thuongHieu.setMa(this.ma);
        thuongHieu.setTen(this.ten);
        thuongHieu.setTrangThai(this.trangThai);
        thuongHieu.setNgayTao(DatetimeUtil.getCurrentDate());
        return thuongHieu;
    }
}
