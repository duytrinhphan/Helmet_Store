package com.example.demo.core.Admin.service.InterfaceHoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonChitietResponse;
import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;

import java.util.List;

public interface AdDetailHoaDonChiTietService {

    List<AdminHoaDonChitietResponse> getHoaDonChiTietByIdHD(Integer id);
}
