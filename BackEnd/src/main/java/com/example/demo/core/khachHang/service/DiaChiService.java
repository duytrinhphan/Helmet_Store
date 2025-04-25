package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.request.KHDiaChiRequest;
import com.example.demo.core.khachHang.model.response.DiaChiResponse;
import com.example.demo.entity.DiaChi;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface DiaChiService {

    List<DiaChi> getAll(String token);

//    List<DiaChi> getUserByDiaChi(Integer idUser);

    DiaChi addDiaChi(KHDiaChiRequest request,String token);

    DiaChi updateDiaChi(KHDiaChiRequest request, Integer id ,String token);

    Optional<DiaChi> delete(Integer id);

    DiaChi thietLapMacDinh(Integer id, String token);

    DiaChi findDiaChiByIdUserAndTrangThai(String token);
}
