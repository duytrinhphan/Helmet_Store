package com.example.demo.core.Admin.service.impl.HoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonChitietResponse;
import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.repository.AdHoaDonChiTietReponsitory;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdDetailHoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDetailHoaDonChiTietServiceImpl implements AdDetailHoaDonChiTietService {
    @Autowired
     private AdHoaDonChiTietReponsitory hoaDonChiTietReponsitory;

    @Override
    public List<AdminHoaDonChitietResponse> getHoaDonChiTietByIdHD(Integer id) {
        return hoaDonChiTietReponsitory.getHoaDonChiTietByIdHD(id);
    }
}
