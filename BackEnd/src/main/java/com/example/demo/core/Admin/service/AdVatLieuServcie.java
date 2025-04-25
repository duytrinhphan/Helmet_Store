package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminVatLieuRequest;
import com.example.demo.entity.VatLieu;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface AdVatLieuServcie {
    Page<VatLieu> getAll(Integer page);

    List<VatLieu> findAll();

    List<VatLieu> getAllByTrangThai(Integer trangThai);

    VatLieu getById(Integer id);

    Page<VatLieu> search(String keyword, Integer page);

    HashMap<String, Object> add(AdminVatLieuRequest dto);

    HashMap<String, Object> update(AdminVatLieuRequest dto, Integer id);

    HashMap<String, Object> delete(Integer id);

    void saveExcel(MultipartFile file);
}
