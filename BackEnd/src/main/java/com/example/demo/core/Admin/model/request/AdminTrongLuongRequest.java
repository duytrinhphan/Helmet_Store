package com.example.demo.core.Admin.model.request;

import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.entity.TrongLuong;
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
public class AdminTrongLuongRequest implements DtoToEntity<TrongLuong> {

    private Integer id;

    @NotBlank(message = "Không bỏ trống đơn vị")
    private String donVi;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private Integer trangThai;

    private Integer value;

    @Override
    public TrongLuong dtoToEntity(TrongLuong e) {
        e.setMa(this.ma);
        e.setNgayTao(DatetimeUtil.getCurrentDate());
        e.setDonVi(this.donVi);
        e.setTrangThai(this.trangThai);
        e.setValue(this.value);
        return e;
    }
}
