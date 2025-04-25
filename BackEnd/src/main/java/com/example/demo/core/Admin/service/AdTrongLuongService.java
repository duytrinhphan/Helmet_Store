package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminTrongLuongRequest;
import com.example.demo.entity.TrongLuong;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface AdTrongLuongService {

    Page<TrongLuong> getAll(Integer page);

    List<TrongLuong> findAll();

    List<TrongLuong> getAllByTrangThai(Integer trangThai);

    TrongLuong getById(Integer id);

    Page<TrongLuong> search(String keyword, Integer page);

    HashMap<String, Object> add(AdminTrongLuongRequest TrongLuong);

    HashMap<String,Object> update(AdminTrongLuongRequest trongLuongRequest, Integer id);

    HashMap<String,Object> delete(Integer id);

    void saveExcel(MultipartFile file);
}
