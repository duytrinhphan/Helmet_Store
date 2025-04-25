package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.response.AdminSPCTResponse;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.service.AdSanPhamChiTietService;
import com.example.demo.entity.SanPhamChiTiet;
import com.microsoft.azure.storage.StorageException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;
@Service
public class SanPhamCTServiceImpl implements AdSanPhamChiTietService {

    @Autowired
    AdChiTietSanPhamReponsitory ctspRepo;
    @Override
    public Page<SanPhamChiTiet> getAll(Integer page, String upAndDown, Integer trangThai) {
        return null;
    }

    @Override
    public SanPhamChiTiet getOne(Integer id) {
        return null;
    }

    @Override
    public AdminSanPhamChiTietResponse add(AdminSanPhamChiTietRequest dto) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        return null;
    }

    @Override
    public AdminSanPhamChiTietResponse update(AdminSanPhamChiTietRequest dto, Integer id) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
        return null;
    }

    @Override
    public SanPhamChiTiet delete(Integer id) {
        return null;
    }

    @Override
    public void saveExcel(MultipartFile file) throws IOException, StorageException, InvalidKeyException, URISyntaxException {

    }

    @Override
    public List<SanPhamChiTiet> exportCustomerToExcel(HttpServletResponse response) throws IOException {
        return null;
    }

    @Override
    public List<AdminSPCTResponse> listCTSPBySanPham(Integer idsp) {
        return ctspRepo.getListCTSPBySanPham(idsp);
    }
}
