package com.example.demo.core.Admin.repository;

import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.reponsitory.ChiTietSanPhamReponsitory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdSanPhamChiTietRepository extends ChiTietSanPhamReponsitory {
    List<SanPhamChiTiet> findAllByTrangThaiAndSoLuongTonGreaterThan(Integer trangThai, Integer soLuongTon);
}
