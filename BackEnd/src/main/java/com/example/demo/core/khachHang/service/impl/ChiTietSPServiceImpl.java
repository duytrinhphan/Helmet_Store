package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.repository.KHchiTietSanPhamRepository;
import com.example.demo.core.khachHang.service.ChiTietSPService;
import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ChiTietSPServiceImpl implements ChiTietSPService{
    @Autowired
    private KHchiTietSanPhamRepository repo;

    @Override
    public Page<SanPhamChiTiet> findAllByTenLoai(String tenLoai) {
        Pageable pageable = PageRequest.of(0,6);
        System.out.println(tenLoai);
        return repo.findByLoai(tenLoai,pageable);
    }

    @Override
    public Page<SanPhamChiTiet> findAllByNgayTao() {
        Sort sort = Sort.by(Sort.Direction.DESC,"ngayTao");
        Pageable pageable = PageRequest.of(0,6,sort);
        return repo.findAllByTrangThai(1,pageable);
    }

}
