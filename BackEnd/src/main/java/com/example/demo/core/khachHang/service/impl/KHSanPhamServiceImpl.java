package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.response.SanPhamResponse;
import com.example.demo.core.khachHang.repository.KHSanPhamRepository;
import com.example.demo.core.khachHang.service.KHSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KHSanPhamServiceImpl implements KHSanPhamService {

    @Autowired
    private KHSanPhamRepository repository;

    @Override
    public List<SanPhamResponse> getAll() {
        return repository.getAllSP();
    }

}
