package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminKhuyenMaiRequest;
import com.example.demo.core.Admin.model.response.AdminKhuyenMaiResponse;
import com.example.demo.entity.KhuyenMai;
import com.example.demo.entity.SanPhamChiTiet;

import java.util.HashMap;
import java.util.List;

public interface AdKhuyenMaiService {

    List<AdminKhuyenMaiResponse> getAllKhuyenMai();

    HashMap<String, Object> add(AdminKhuyenMaiRequest khuyenMaiRequest);

    HashMap<String,Object> update(AdminKhuyenMaiRequest khuyenMaiRequest, Integer id);

    HashMap<String,Object> delete(AdminKhuyenMaiRequest khuyenMaiRequest, Integer id);

    KhuyenMai getKhuyenMaiById(Integer id);

    List<SanPhamChiTiet> getAllSPCTByKhuyenMai();

    HashMap<String, Object>  updateProductDetail(Integer productId, Integer idkm);

}
