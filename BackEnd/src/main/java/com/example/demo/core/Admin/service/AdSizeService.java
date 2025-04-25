package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminSizeRequest;
import com.example.demo.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface AdSizeService {
    Page<Size> getAll(Integer page);

    List<Size> findAll();

    List<Size> getAllByTrangThai(Integer trangThai);

    Size getById(Integer id);

    Page<Size> search(String keyword, Integer page);

    HashMap<String, Object> add(AdminSizeRequest dto);

    HashMap<String, Object> update(AdminSizeRequest dto, Integer id);

    HashMap<String, Object> delete(Integer id);

    void saveExcel(MultipartFile file);
}
