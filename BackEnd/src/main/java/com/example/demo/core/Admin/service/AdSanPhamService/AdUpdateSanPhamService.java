package com.example.demo.core.Admin.service.AdSanPhamService;

import com.example.demo.core.Admin.model.request.AdminAddImageRequest;
import com.example.demo.core.Admin.model.request.AdminImageRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.response.*;
import com.example.demo.entity.*;
import com.microsoft.azure.storage.StorageException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AdUpdateSanPhamService {
    AdminSanPhamChiTiet2Response update(AdminSanPhamChiTietRequest dto, Integer id) throws URISyntaxException, StorageException, InvalidKeyException, IOException, ExecutionException, InterruptedException;

    AdminImageResponse updateImage(Integer id, AdminAddImageRequest dto) throws URISyntaxException, StorageException, InvalidKeyException, IOException;

    SanPhamDOT updateSanPham(Integer id, AdminSanPhamRequest dto) throws URISyntaxException, StorageException, InvalidKeyException, IOException;

    SanPham saveSanPham(AdminSanPhamRequest request);

    AdminSanPhamChiTiet2Response delete(Integer id);

    Boolean check(Integer idSize, Integer idMau,Integer idSP);

    AdminSanPhamChiTiet2Response saveSanPhamChiTiet(AdminSanPhamChiTietRequest dto) throws IOException, StorageException, InvalidKeyException, URISyntaxException;

    AdminSanPhamChiTiet2Response khoiPhuc(Integer id);

    AdminImageResponse deleteImg(Integer id);

    AdminImageResponse saveImage(Integer idSP, AdminAddImageRequest adminAddImageRequest) throws IOException, StorageException, InvalidKeyException, URISyntaxException;
}
