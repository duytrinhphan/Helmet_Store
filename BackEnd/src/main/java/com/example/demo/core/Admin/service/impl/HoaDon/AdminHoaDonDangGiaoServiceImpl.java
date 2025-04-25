package com.example.demo.core.Admin.service.impl.HoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.repository.AdHoaDonChiTietReponsitory;
import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonDangGiaoService;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHoaDonDangGiaoServiceImpl implements AdHoaDonDangGiaoService {

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    @Autowired
    private AdHoaDonChiTietReponsitory hdctRepo;

    @Override
    public AdminHoaDonResponse xacNhanHoaDon(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        if (hoaDon != null) {
            List<HoaDonChiTiet> lstHDCT = hdctRepo.findByIdHoaDon(hoaDon.getId(), sort);
            for (HoaDonChiTiet hdct : lstHDCT) {
                hdct.setTrangThai(3);
                hdctRepo.save(hdct);
            }

            hoaDon.setNgayThanhToan(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setNgayNhan(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setTrangThai(HoaDonStatus.HOAN_THANH);
            HoaDon hd = hoaDonReponsitory.save(hoaDon);
            return hoaDonReponsitory.getByIds(hd.getId());
        } else {
            return null;
        }
    }
}
