package com.example.demo.core.khachHang.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPayload {

    private String ten;

    private String email;

    private String ma;

    private String sdt;

    private String matKhau;

    private Integer gioiTinh;

    private Date ngaySinh;
}
