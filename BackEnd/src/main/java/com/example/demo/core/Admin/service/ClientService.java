package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminUserRequest;
import com.example.demo.core.Admin.model.request.OTPResquest;

public interface ClientService {
    Boolean create(AdminUserRequest sdi);

    Boolean createOTP(OTPResquest otpRequest);
}
