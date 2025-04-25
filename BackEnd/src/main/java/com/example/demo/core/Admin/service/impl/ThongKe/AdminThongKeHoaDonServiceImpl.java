package com.example.demo.core.Admin.service.impl.ThongKe;

import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.service.AdThongKeService.AdThongKeHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminThongKeHoaDonServiceImpl implements AdThongKeHoaDonService {

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    @Override
    public Integer tongDonhang() {
        return hoaDonReponsitory.tongDonhang();
    }

    @Override
    public Integer tongDonhangHoanThanh() {
        return hoaDonReponsitory.tongDonhangHoanThanh();
    }

    @Override
    public Integer tongDonhangDangGiao() {
        return hoaDonReponsitory.tongDonhangDangGiao();
    }

    @Override
    public Integer tongDonhangHuy() {
        return hoaDonReponsitory.tongDonhangHuy();
    }

    @Override
    public Integer tongDonhangHoanTra() {
        return hoaDonReponsitory.tongDonhangHoanTra();
    }
}
