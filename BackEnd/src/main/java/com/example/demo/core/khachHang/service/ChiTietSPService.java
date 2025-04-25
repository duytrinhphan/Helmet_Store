package com.example.demo.core.khachHang.service;

import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChiTietSPService {

    Page<SanPhamChiTiet> findAllByTenLoai(String tenLoai);

    Page<SanPhamChiTiet> findAllByNgayTao();

}
