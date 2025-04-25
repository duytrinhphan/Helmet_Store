package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.response.KhVoucherResponse;
import com.example.demo.core.khachHang.model.response.VoucherResponse;

import java.util.List;

public interface KHVoucherService {

    List<VoucherResponse> listVoucher(Integer id);


}
