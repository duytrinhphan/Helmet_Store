package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.request.RegisterPayload;
import com.example.demo.entity.User;

public interface RegisterService {

    User registerUser(RegisterPayload payload);
}
