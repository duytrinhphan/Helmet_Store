package com.example.demo.core.Admin.service.impl.HoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdHoaDonChiTietReponsitory;
import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonChoXacNhanService;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminHoaDonChoXacNhanServiceImpl implements AdHoaDonChoXacNhanService {

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    @Autowired
    private AdHoaDonChiTietReponsitory hdctRepo;

    @Autowired
    private AdChiTietSanPhamReponsitory ctspRepo;

    @Override
    public List<AdminHoaDonResponse> getHoaDonChoXacNhan() {
        return hoaDonReponsitory.getHoaDonTrangThai(HoaDonStatus.YEU_CAU_XAC_NHAN);
    }


    @Override
    public AdminHoaDonResponse huyHoaDonChoXacNhan(Integer idHD, String lyDo) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        HashMap<Integer, Integer> quantityMap = new HashMap<>();
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setTrangThai(HoaDonStatus.DA_HUY);
            hoaDon.setLyDo(lyDo);
            HoaDon hd = hoaDonReponsitory.save(hoaDon);
            List<HoaDonChiTiet> lstHDCT = hdctRepo.findByIdHoaDon(idHD, sort);
            for (HoaDonChiTiet hdct : lstHDCT) {
                int idSP = hdct.getSanPhamChiTiet().getId();
                int quantity = hdct.getSoLuong();
                if (quantityMap.containsKey(idSP)) {
                    quantityMap.put(idSP, quantityMap.get(idSP) + quantity);
                } else {
                    quantityMap.put(idSP, quantity);
                }
                hdct.setTrangThai(0);
                hdctRepo.save(hdct);
            }
            for (Map.Entry<Integer, Integer> entry : quantityMap.entrySet()) {
                int idSP = entry.getKey();
                int quantity = entry.getValue();
                SanPhamChiTiet spct = ctspRepo.findById(idSP).get();
                spct.setSoLuongTon(spct.getSoLuongTon() + quantity);
                ctspRepo.save(spct);
            }
            return hoaDonReponsitory.getByIds(hd.getId());
        } else {
            return null;
        }
    }

    @Override
    public AdminHoaDonResponse xacNhanHoaDon(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setTrangThai(HoaDonStatus.DANG_CHUAN_BI_HANG);
            HoaDon hd = hoaDonReponsitory.save(hoaDon);
            return hoaDonReponsitory.getByIds(hd.getId());
        } else {
            return null;
        }
    }
}
