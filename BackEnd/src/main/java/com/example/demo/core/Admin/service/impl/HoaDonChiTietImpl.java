package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.response.AdminHoaDonChitietResponse;
import com.example.demo.core.Admin.repository.AdHoaDonChiTietReponsitory;
import com.example.demo.core.Admin.service.AdHoaDonChiTietService;
import com.example.demo.entity.HoaDonChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonChiTietImpl implements AdHoaDonChiTietService {
    @Autowired
    private AdHoaDonChiTietReponsitory repo;

    @Override
    public List<AdminHoaDonChitietResponse> findHDCTByIdHoaDon(Integer idHD) {
        Sort sort = Sort.by(Sort.Direction.DESC, "ngayTao");
        return repo.findHDCTByIDHD(idHD);
    }

    @Override
    public List<AdminHoaDonChitietResponse> findHDCTByTrangThai(Integer trangThai) {
        return repo.findHDCTByTrangThai(trangThai);
    }
}
