package com.example.demo.core.Admin.model.request;

import com.example.demo.entity.Image;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.util.DatetimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminImageRequest implements DtoToEntity<Image> {

    private Integer id;

    private String anh;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private String trangThai;

    private String sanPhamChiTiet;

    @Override
    public Image dtoToEntity(Image image) {
        image.setTrangThai(Integer.valueOf(this.trangThai));
        image.setNgayTao(DatetimeUtil.getCurrentDate());
        return image;
    }
}
