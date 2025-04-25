package com.example.demo.core.Admin.service.AdThongKeService;

import com.example.demo.core.Admin.model.response.AdminThongKeBO;
import com.example.demo.core.Admin.model.response.AdminThongKeNowDayBO;
import com.example.demo.core.Admin.model.response.xuatBaoCaoThongKeRespone;

import java.time.LocalDateTime;
import java.util.List;

public interface AdThongKeDoanhThuService {

    AdminThongKeBO getAll();

    AdminThongKeNowDayBO getAllByNowDay();

    AdminThongKeBO getAllBySanPham(Integer id,String year);

    AdminThongKeBO getAllByThuongHieu(Integer id,String year);

    AdminThongKeBO getAllByLoai(Integer id,String year);

    AdminThongKeBO getAllByYear(String year);

    AdminThongKeBO getAllByMonth(LocalDateTime startDate, LocalDateTime endDate);

    AdminThongKeBO getAllByPhuongThuc(Integer idPhuongThucThanhToan);

    List<xuatBaoCaoThongKeRespone> xuatBaoCaoThongKe();

}
