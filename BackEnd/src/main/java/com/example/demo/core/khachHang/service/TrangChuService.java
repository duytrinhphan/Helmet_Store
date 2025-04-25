package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.response.TrangChuResponse;

import java.util.List;

public interface TrangChuService {

    List<TrangChuResponse> getAll();

    List<TrangChuResponse> getAllByTenLoai(Integer tenLoai);

    List<TrangChuResponse> getSPBanChay();

}
