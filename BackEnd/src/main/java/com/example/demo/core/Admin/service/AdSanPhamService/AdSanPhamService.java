package com.example.demo.core.Admin.service.AdSanPhamService;

import com.example.demo.core.Admin.model.request.AdminSanPhamRepuest2;
import com.example.demo.core.Admin.model.response.*;
import com.example.demo.entity.Image;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.SanPhamChiTiet;
import com.microsoft.azure.storage.StorageException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;

public interface AdSanPhamService {


    List<AdminSanPhamResponse> getAll();
    List<SanPhamDOT> getAlls();

    SanPhamDOT findByIdSP(Integer id);

    List<AdminSanPhamChiTiet2Response> findBySanPhamCT(Integer id);

    List<AdminImageResponse> getProductImages(Integer idProduct);

    Boolean findBySanPhamTen(AdminSanPhamRepuest2 request);

    List<SanPhamDOT> loc(String comboBoxValue) ;

    SanPhamDOT save(AdminSanPhamRepuest2 adminSanPhamRepuest2) throws IOException, StorageException, InvalidKeyException, URISyntaxException;


    List<SanPhamChiTiet> saveSanPhamChiTiet(AdminSanPhamRepuest2 repuest2, SanPham sanPham) throws URISyntaxException, StorageException, InvalidKeyException, IOException;

    SanPhamDOT delete(Integer id);

    SanPhamDOT khoiPhuc(Integer id);

    List<AdminSanPhamChiTiet2Response> locCTSP(String comboBoxValue);

    List<AdminSanPhamResponse> getSanPhamByIdLoai( Integer idloai);

    List<AdminSanPhamResponse> getSanPhamByIdThuongHieu( Integer idthuonghieu);
}
