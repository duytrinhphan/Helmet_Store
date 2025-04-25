package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminVoucherRequest;
import com.example.demo.core.Admin.model.response.AdminVoucherGetUserResponse;
import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;


public interface AdVoucherService {

    List<Voucher> getAllVoucher();

    Voucher getById(Integer id);

    Page<Voucher> search(String keyword, Integer page);

    HashMap<String, Object> add(AdminVoucherRequest Voucher);

    HashMap<String, Object> update(AdminVoucherRequest voucherRequest, Integer id);

    HashMap<String, Object> delete(AdminVoucherRequest voucherRequestRequest, Integer id);

    List<AdminVoucherGetUserResponse> getUserByVoucher(Integer idVoucher);

}
