package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.response.AdminHoaDonChitietResponse;
import com.example.demo.entity.HoaDonChiTiet;

import java.util.List;

public interface AdHoaDonChiTietService {

    List<AdminHoaDonChitietResponse> findHDCTByIdHoaDon(Integer idHD);

    List<AdminHoaDonChitietResponse> findHDCTByTrangThai(Integer trangThai);

}
