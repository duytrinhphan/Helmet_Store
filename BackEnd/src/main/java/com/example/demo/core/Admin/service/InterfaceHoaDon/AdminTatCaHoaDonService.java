package com.example.demo.core.Admin.service.InterfaceHoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.entity.HoaDon;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AdminTatCaHoaDonService {

    List<AdminHoaDonResponse> getAll();

    List<AdminHoaDonResponse> getAllByHinhThucGiao(Integer hinhThucGiao);

    List<AdminHoaDonResponse> getAllByPttt(Integer pttt);

    List<AdminHoaDonResponse> getAllByHinhThucGiaoAndPttt(Integer hinhThucGiao, Integer pttt);

    List<AdminHoaDonResponse> getHoaDonTrangThai(Integer trangThai);

    List<AdminHoaDonResponse> getHoaDonTrangThaiAndHinhThucGiao(Integer trangThai, Integer hinhThucGiao);

    List<AdminHoaDonResponse> getHoaDonTrangThaiAndPttt(Integer trangThai, Integer pttt);

    List<AdminHoaDonResponse> getHoaDonTrangThaiAndPtttAndHtgh(Integer trangThai, Integer pttt, Integer hinhThucGiao);

    List<AdminHoaDonResponse> getHoaDonHoanThanh();

    List<AdminHoaDonResponse> getHoaDonHuy();

    List<AdminHoaDonResponse> getHoaDonDangGiao();

    List<AdminHoaDonResponse> getHoaDonYeuCauDoiTra();

    List<AdminHoaDonResponse> getHoaDonXacNhanDoiTra();

    List<AdminHoaDonResponse> getHoaDonDangChuanBiHang();

    List<AdminHoaDonResponse> searchDate(LocalDateTime startDate, LocalDateTime endDate, String comboBoxValue);

    List<AdminHoaDonResponse> searchDateAndHinhThucGiao(LocalDateTime startDate, LocalDateTime endDate, String comboBoxValue, Integer hinhThucGiao);

    List<AdminHoaDonResponse> searchDateAndPttt(LocalDateTime startDate, LocalDateTime endDate, String comboBoxValue, Integer pttt);

    List<AdminHoaDonResponse> searchDateAndPtttAndHtgh(LocalDateTime startDate, LocalDateTime endDate, String comboBoxValue, Integer pttt, Integer hinhThucGiao);

    List<AdminHoaDonResponse> searchDateByTrangThai(LocalDateTime startDate, LocalDateTime endDate, String comboBoxValue, Integer trangThai);

    List<AdminHoaDonResponse> searchDateByTrangThaiAndHinhThucGiao(LocalDateTime startDate, LocalDateTime endDate, String comboBoxValue, Integer trangThai, Integer hinhThucGiao);

    List<AdminHoaDonResponse> searchDateByTrangThaiAndPttt(LocalDateTime startDate, LocalDateTime endDate, String comboBoxValue, Integer trangThai, Integer pttt);

    List<AdminHoaDonResponse> searchDateByTrangThaiAndPtttAndHtgh(LocalDateTime startDate, LocalDateTime endDate, String comboBoxValue, Integer trangThai, Integer pttt, Integer hinhThucGiao);

    AdminHoaDonResponse giaoHoaDonChoVanChuyen(Integer idHD, LocalDateTime ngayShip,Integer tongTien,Integer tienShip);

}
