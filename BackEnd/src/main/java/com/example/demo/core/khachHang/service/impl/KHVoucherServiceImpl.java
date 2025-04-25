package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.response.KhVoucherResponse;
import com.example.demo.core.khachHang.model.response.VoucherResponse;
import com.example.demo.core.khachHang.repository.KHVoucherRepository;
import com.example.demo.core.khachHang.service.KHVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KHVoucherServiceImpl implements KHVoucherService {

    @Autowired
    KHVoucherRepository khVoucherRepo;
    @Override
    public List<VoucherResponse> listVoucher(Integer id) {
        return khVoucherRepo.listVoucher(id);
    }


}
