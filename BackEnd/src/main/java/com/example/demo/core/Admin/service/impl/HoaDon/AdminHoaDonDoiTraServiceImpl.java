package com.example.demo.core.Admin.service.impl.HoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonChitietResponse;
import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdHoaDonChiTietReponsitory;
import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonDoiTraService;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminHoaDonDoiTraServiceImpl implements AdHoaDonDoiTraService {

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    @Autowired
    private AdHoaDonChiTietReponsitory hdctRepo;

    @Autowired
    private AdChiTietSanPhamReponsitory ctspRepo;

    @Override
    public AdminHoaDonChitietResponse huyHoaDonTrahang(Integer idHD, String lyDo, Integer idSPCT) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        SanPhamChiTiet spct = ctspRepo.findById(idSPCT).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        HashMap<Integer, Integer> quantityMap = new HashMap<>();
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setTrangThai(HoaDonStatus.HUY_DOI_TRA);
            hoaDon.setMoTa(lyDo);
            HoaDon hd = hoaDonReponsitory.save(hoaDon);
            List<HoaDonChiTiet> lstHDCT = hdctRepo.findByIdHoaDon(idHD, sort);
            HoaDonChiTiet hdctRespone = new HoaDonChiTiet();
            for (HoaDonChiTiet hdct : lstHDCT) {
                if (hdct.getSanPhamChiTiet().getId() == spct.getId() && hdct.getTrangThai() == HoaDonStatus.YEU_CAU_DOI_TRA) {
//                    int count = 0;
//                    for (HoaDonChiTiet hdct1 : lstHDCT) {
//                        if (hdct1.getSanPhamChiTiet().getId() == spct.getId() && hdct1.getTrangThai() != HoaDonStatus.YEU_CAU_DOI_TRA) {
//                            count = 1;
//                            hdct1.setSoLuong(hdct1.getSoLuong() + hdct.getSoLuong());
//                            hdctRepo.save(hdct1);
//                        }
//                    }
//                    if (count == 0){
//                        hdct.setTrangThai(HoaDonStatus.HUY_DOI_TRA);
//                        hdctRespone = hdctRepo.save(hdct);
//                    }else{
//                        hdct.setTrangThai(HoaDonStatus.HUY_DOI_TRA);
//                        hdctRespone = hdctRepo.save(hdct);
//                    }
                    hdct.setTrangThai(HoaDonStatus.HUY_DOI_TRA);
                    hdctRespone = hdctRepo.save(hdct);
                }

            }
            return hdctRepo.findByIdHDCT(hdctRespone.getId());
        } else {
            return null;
        }
    }

    @Override
    public AdminHoaDonChitietResponse xacNhanHoaDonTraHang(Integer idHD, Integer idSPCT) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        HashMap<Integer, Integer> quantityMap = new HashMap<>();
        if (hoaDon != null) {
            List<HoaDonChiTiet> lstHDCT = hdctRepo.findByIdHoaDon(hoaDon.getId(), sort);
            HoaDonChiTiet hdctRespone = new HoaDonChiTiet();
            Long sum = 0l;
            Long tienTra = 0l;
            for (HoaDonChiTiet hdct : lstHDCT) {
                if (hdct.getSanPhamChiTiet().getId() == idSPCT && hdct.getTrangThai() == 7) {
                    hdct.setTrangThai(HoaDonStatus.XAC_NHAN_DOI_TRA);
                    tienTra =  hdct.getSoLuong() *  Long.valueOf(String.valueOf(hdct.getDonGia()));
                    hdctRespone = hdctRepo.save(hdct);
                }
                if (hdct.getSanPhamChiTiet().getId() == idSPCT && hdct.getTrangThai() == HoaDonStatus.HOAN_THANH) {
                    hdct.setSoLuong(hdct.getSoLuong() - hdctRespone.getSoLuong());
                    hdctRepo.save(hdct);
                }
                if ( hdct.getTrangThai() == 3 ) {
                 Long tongTien =    hdct.getSoLuong() *  Long.valueOf(String.valueOf(hdct.getDonGia()));
                  sum += tongTien;
                }
            }
            if(hoaDon.getTienSauKhiGiam() == hoaDon.getTongTien()){
                hoaDon.setTienSauKhiGiam(BigDecimal.valueOf(sum));
                hoaDon.setTongTien(BigDecimal.valueOf(sum));
            }else{
                hoaDon.setTienSauKhiGiam( hoaDon.getTienSauKhiGiam().subtract(BigDecimal.valueOf(tienTra)));
                hoaDon.setTongTien(BigDecimal.valueOf(sum));
            }
            hoaDonReponsitory.save(hoaDon);

            return hdctRepo.findByIdHDCT(hdctRespone.getId());
        } else {
            return null;
        }
    }

    public AdminHoaDonResponse khongCongSoLuongSP(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setTrangThai(HoaDonStatus.HOAN_THANH_DOI_TRA);
            HoaDon hd = hoaDonReponsitory.save(hoaDon);
            List<HoaDonChiTiet> lstHDCT = hdctRepo.findByIdHoaDon(idHD, sort);
            for (HoaDonChiTiet hdct : lstHDCT) {
                hdct.setTrangThai(HoaDonStatus.HOAN_THANH_DOI_TRA);
                HoaDonChiTiet hdct2 = hdctRepo.save(hdct);
                hdct2.setMa("HDCT" + hdct2.getId());
                hdctRepo.save(hdct2);
            }
            return hoaDonReponsitory.getByIds(hd.getId());
        }
        return null;
    }

    @Override
    public AdminHoaDonResponse congSoLuongSP(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        HashMap<Integer, Integer> quantityMap = new HashMap<>();
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setTrangThai(HoaDonStatus.HOAN_THANH_DOI_TRA);
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
                hdct.setTrangThai(HoaDonStatus.HOAN_THANH_DOI_TRA);
                HoaDonChiTiet hdct2 = hdctRepo.save(hdct);
                hdct2.setMa("HDCT" + hdct2.getId());
                hdctRepo.save(hdct2);
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
}
