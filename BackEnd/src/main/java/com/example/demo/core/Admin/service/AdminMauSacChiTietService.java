package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.response.AdminMauSacChiTietResponse;
import com.microsoft.azure.storage.StorageException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

public interface AdminMauSacChiTietService {

//    AdminMauSacChiTietResponse add(AdminMauSacChiTietRequest request) throws IOException, StorageException, InvalidKeyException, URISyntaxException;
//
//    AdminMauSacChiTietResponse update(Integer id, AdminMauSacChiTietRequest request) throws IOException, StorageException, InvalidKeyException, URISyntaxException;

     void delete(Integer id);

    Boolean check(Integer idSP, Integer idMauSac);
    Boolean checks(Integer idSP, Integer idMauSac,Integer idSizeCT);
}
