package com.example.demo.core.Admin.service.impl.ThongKe;

import com.example.demo.core.Admin.model.response.AdminThongKeLoiNhuanBo;
import com.example.demo.core.Admin.model.response.AdminThongKeLoiNhuanHoaDonResponse;
import com.example.demo.core.Admin.model.response.AdminThongKeLoiNhuanSanPhamResponse;
import com.example.demo.core.Admin.repository.AdThongKeLoiNhuanRespository;
import com.example.demo.core.Admin.service.AdThongKeService.AdThongKeLoiNhuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminThongKeLoiNhuanServiceIpml implements AdThongKeLoiNhuanService {

    @Autowired
    private AdThongKeLoiNhuanRespository adThongKeLoiNhuanRespository;

    @Override
    public AdminThongKeLoiNhuanBo getAll(String year, String startDate, String endDate) {
        Integer tongLoiNhuan = adThongKeLoiNhuanRespository.tongLoiNhuan(year, startDate, endDate);
        Integer tongDonhangHoanThanh = adThongKeLoiNhuanRespository.tongDonhangHoanThanh(year, startDate, endDate);
        Integer tongDonhangDangGiao = adThongKeLoiNhuanRespository.tongDonhangDangGiao(year, startDate, endDate);
        Integer tongDonhangHuy = adThongKeLoiNhuanRespository.tongDonhangHuy(year, startDate, endDate);

        List<AdminThongKeLoiNhuanSanPhamResponse> lstSanPham = adThongKeLoiNhuanRespository.lstSanPhamLoiNhuan(year, startDate, endDate);
        List<AdminThongKeLoiNhuanHoaDonResponse> lstHoaDon = adThongKeLoiNhuanRespository.lstHoaDonLoiNhuan(year, startDate, endDate);
        AdminThongKeLoiNhuanBo adminThongKeLoiNhuanBo = new AdminThongKeLoiNhuanBo(tongLoiNhuan,tongDonhangHoanThanh,tongDonhangDangGiao,tongDonhangHuy, lstSanPham, lstHoaDon);
        return adminThongKeLoiNhuanBo;
    }

    public AdminThongKeLoiNhuanBo getAllByHinhThucGiaoHang(Integer idInteger) {
        Integer tongLoiNhuan = adThongKeLoiNhuanRespository.tongLoiNhuanByHinhThuc(idInteger);
        Integer tongDonhangHoanThanh = adThongKeLoiNhuanRespository.tongDonhangHoanThanhByHinhThuc(idInteger);
        Integer tongDonhangDangGiao = adThongKeLoiNhuanRespository.tongDonhangDangGiaoByHinhThuc(idInteger);
        Integer tongDonhangHuy = adThongKeLoiNhuanRespository.tongDonhangHuyByHinhThuc(idInteger);

        List<AdminThongKeLoiNhuanSanPhamResponse> lstSanPham = adThongKeLoiNhuanRespository.lstSanPhamLoiNhuanByHinhThuc(idInteger);
        List<AdminThongKeLoiNhuanHoaDonResponse> lstHoaDon = adThongKeLoiNhuanRespository.lstHoaDonLoiNhuanByHinhThuc(idInteger);
        AdminThongKeLoiNhuanBo adminThongKeLoiNhuanBo = new AdminThongKeLoiNhuanBo(tongLoiNhuan,tongDonhangHoanThanh,tongDonhangDangGiao,tongDonhangHuy, lstSanPham, lstHoaDon);
        return adminThongKeLoiNhuanBo;
    }
}
