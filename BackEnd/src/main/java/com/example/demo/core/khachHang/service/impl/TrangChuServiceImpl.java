package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.response.TrangChuResponse;
import com.example.demo.core.khachHang.repository.TrangChuRepository;
import com.example.demo.core.khachHang.service.TrangChuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrangChuServiceImpl implements TrangChuService {

    @Autowired
    private TrangChuRepository trangChuRepo;

    @Override
    public List<TrangChuResponse> getAll() {
        Pageable pageable = PageRequest.of(0, 10);
        return trangChuRepo.getAll(pageable);
    }

    @Override
    public List<TrangChuResponse> getAllByTenLoai(Integer tenLoai) {
        Pageable pageable = PageRequest.of(0, 10);
        return trangChuRepo.getAllByTenLoai(tenLoai, pageable);
    }

    @Override
    public List<TrangChuResponse> getSPBanChay() {
        Pageable pageable = PageRequest.of(0, 10);
        return trangChuRepo.getSPBanChay(pageable);
    }

}
