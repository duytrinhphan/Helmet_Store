package com.example.demo.core.Admin.service.impl;


import com.example.demo.core.Admin.model.request.AdminVoucherRequest;
import com.example.demo.core.Admin.model.response.AdminVoucherGetUserResponse;
import com.example.demo.core.Admin.repository.AdVoucherReponsitory;
import com.example.demo.core.Admin.service.AdVoucherService;
import com.example.demo.entity.Voucher;
import com.example.demo.reponsitory.VoucherReponsitory;
import com.example.demo.util.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
//@EnableScheduling
public class VoucherServiceImpl implements AdVoucherService {

    @Autowired
    AdVoucherReponsitory voucherReponsitory;

    @Override
    public List<Voucher> getAllVoucher() {
        return voucherReponsitory.getVoucherByTrangThai();
    }

    @Override
    public Voucher getById(Integer id) {

        Optional<Voucher> optional = this.voucherReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<Voucher> search(String keyword, Integer page) {
        return null;
    }

    @Override
    public HashMap<String, Object> add(AdminVoucherRequest dto) {

        Voucher voucher = dto.dtoToEntity(new Voucher());
        LocalDateTime thoiGianHienTai = LocalDateTime.now();
        try {
            if (voucher.getThoiGianKetThuc().isBefore(thoiGianHienTai)) {
                voucher.setTrangThai(1);
            } else if (voucher.getThoiGianBatDau().isAfter(thoiGianHienTai)) {
                voucher.setTrangThai(3);
            } else {
                voucher.setTrangThai(0);
            }
            Voucher vouchers = voucherReponsitory.save(voucher);
            return DataUltil.setData("success", vouchers);
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(AdminVoucherRequest dto, Integer id) {
        LocalDateTime currentTime = LocalDateTime.now();
        Optional<Voucher> optional = voucherReponsitory.findById(id);
        if (optional.isPresent()) {
            Voucher voucher = optional.get();
            voucher.setTen(dto.getTen());
            voucher.setMoTa(dto.getMoTa());
            voucher.setThoiGianBatDau(dto.getThoiGianBatDau());
            voucher.setThoiGianKetThuc(dto.getThoiGianKetThuc());
            voucher.setSoLuong(dto.getSoLuong());
            voucher.setGiamToiDa(dto.getGiamToiDa());
            voucher.setGiaTriGiam(dto.getGiaTriGiam());
//            if (currentTime.isBefore(voucher.getThoiGianKetThuc()) && currentTime.isEqual(voucher.getThoiGianKetThuc())) {
//                voucher.setTrangThai(0);
//            }

            if (voucher.getThoiGianKetThuc().isBefore(currentTime)) {
                voucher.setTrangThai(1);
            } else if (voucher.getThoiGianBatDau().isAfter(currentTime)) {
                voucher.setTrangThai(3);
            }
            else {
                voucher.setTrangThai(0);
            }
            try {
                voucherReponsitory.save(voucher);
                return DataUltil.setData("success", voucherReponsitory.save(voucher));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy voucher để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(AdminVoucherRequest dto, Integer id) {

        Optional<Voucher> optional = voucherReponsitory.findById(id);
        if (optional.isPresent()) {
            Voucher voucher = optional.get();
            voucher.setTen(dto.getTen());
            voucher.setMoTa(dto.getMoTa());
            voucher.setThoiGianBatDau(dto.getThoiGianBatDau());
            voucher.setThoiGianKetThuc(dto.getThoiGianKetThuc());
            voucher.setTrangThai(4);
            try {
                voucherReponsitory.save(voucher);
                return DataUltil.setData("success", voucherReponsitory.save(voucher));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy voucher để sửa");
        }
    }

    @Override
    public List<AdminVoucherGetUserResponse> getUserByVoucher(Integer idVoucher) {
        return voucherReponsitory.getUserByVoucher(idVoucher);
    }

    @Scheduled(cron = "0 0 0 * * *") // Lịch chạy hàng ngày vào lúc 00:00:00
    public void updateNgayHetHan() {
        List<Voucher> vouchers = voucherReponsitory.findVoucherByHetHan();
        for (Voucher voucher : vouchers) {
            voucher.setTrangThai(1);
            voucherReponsitory.save(voucher);
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // Lịch chạy hàng ngày vào lúc 00:00:00
    public void updateNgayChuaBatDau() {
        List<Voucher> vouchers = voucherReponsitory.findVoucherByChuaBatDAu();
        for (Voucher voucher : vouchers) {
            voucher.setTrangThai(2);
            voucherReponsitory.save(voucher);
        }


    }

    @Scheduled(cron = "0 0 0 * * *") // Lịch chạy hàng ngày vào lúc 00:00:00
    public void updateNgayConHan() {
        List<Voucher> vouchers = voucherReponsitory.findVoucherByConHan();
        for (Voucher voucher : vouchers) {
            voucher.setTrangThai(0);
            voucherReponsitory.save(voucher);
        }
    }
}
