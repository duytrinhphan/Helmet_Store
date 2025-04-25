package com.example.demo.core.Admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminThongKeLoiNhuanBo {

    private Integer tongLoiNhuan;

    private Integer tongDonhangHoanThanh;

    private Integer tongDonhangDangGiao;

    private Integer tongDonhangHuy;

    private List<AdminThongKeLoiNhuanSanPhamResponse> adminThongKeLoiNhuanSanPhamResponses;

    private List<AdminThongKeLoiNhuanHoaDonResponse> adminThongKeLoiNhuanHoaDonResponses;
}
