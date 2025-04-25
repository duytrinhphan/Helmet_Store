package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.response.KHHoaDonChiTietResponse;
import com.example.demo.core.khachHang.model.response.KhHoaDonTraHangResponse;
import com.example.demo.core.khachHang.repository.KHHoaDonChiTietRepository;
import com.example.demo.core.khachHang.repository.KHUserRepository;
import com.example.demo.core.khachHang.service.KHHoaDonChiTietService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KHHoaDonChiTietServiceImpl implements KHHoaDonChiTietService {

    @Autowired
    private KHHoaDonChiTietRepository hdctRepo;

    @Autowired
    TokenService tokenService;

    @Autowired
    private KHUserRepository repository;

    @Override
    public List<KHHoaDonChiTietResponse> findHDCTByIdHoaDon(Integer idHD) {
        return hdctRepo.findHDCTByIDHD(idHD);
    }

    @Override
    public List<KHHoaDonChiTietResponse> findHDCTByMaHoaDon(String maHD) {
        return hdctRepo.findHDCTByMaHD(maHD);
    }

    @Override
    public List<KhHoaDonTraHangResponse> findHDCTByIdHoaDonTraHang(String token) {
        Integer idKh;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = repository.findAllByUserName(userName);
        idKh = user.getId();
        return hdctRepo.getHoaDonDoiTraTrangThai(idKh);
    }

}
