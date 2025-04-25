package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.request.KHUserRequest;
import com.example.demo.core.khachHang.model.response.KHUserResponse;
import com.microsoft.azure.storage.StorageException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

public interface ThongTinService {

    KHUserResponse getAll(String token);

    KHUserResponse update(KHUserRequest user, Integer id) throws IOException, StorageException, InvalidKeyException, URISyntaxException;
}
