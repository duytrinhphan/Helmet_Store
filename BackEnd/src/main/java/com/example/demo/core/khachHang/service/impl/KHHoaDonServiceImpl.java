package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.response.KHHoaDonResponse;
import com.example.demo.core.khachHang.repository.KHHoaDonChiTietRepository;
import com.example.demo.core.khachHang.repository.KHHoaDonRepository;
import com.example.demo.core.khachHang.repository.KHchiTietSanPhamRepository;
import com.example.demo.core.khachHang.service.KHHoaDonService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KHHoaDonServiceImpl implements KHHoaDonService {

    @Autowired
    private KHHoaDonRepository hdRepo;

    @Autowired
    private KHHoaDonChiTietRepository hdctRepo;

    @Autowired
    private KHchiTietSanPhamRepository ctspRepo;

    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;

    @Override
    public List<KHHoaDonResponse> getAll(String token) {
        Integer idKh;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
      //  log.info("aaaaaaaaaaaaaaaaaa{}",userName);
        User user = userRepository.findByUserName(userName);
        idKh = user.getId();
        return hdRepo.getHoaDonByIdUser(idKh);
    }

    @Override
    public List<KHHoaDonResponse> getHoaDonTrangThai(String token, Integer trangThai,Integer trangThai2,Integer trangThai3,Integer trangThai4) {
        Integer idKh;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idKh = user.getId();
        return hdRepo.getHoaDonTrangThai(idKh, trangThai,trangThai2,trangThai3,trangThai4);
    }

    @Override
    public KHHoaDonResponse huyHoaDonChoXacNhan(Integer idHD, String lyDo) {
        HoaDon hoaDon = hdRepo.findById(idHD).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        HashMap<Integer, Integer> quantityMap = new HashMap<>();
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setTrangThai(HoaDonStatus.DA_HUY);
            hoaDon.setLyDo(lyDo);
            HoaDon hd = hdRepo.save(hoaDon);
            List<HoaDonChiTiet> lstHDCT = hdctRepo.findByIdHoaDon(idHD, sort);
            for (HoaDonChiTiet hdct : lstHDCT) {
                hdct.setTrangThai(0);
                int idSP = hdct.getSanPhamChiTiet().getId();
                int quantity = hdct.getSoLuong();
                if (quantityMap.containsKey(idSP)) {
                    quantityMap.put(idSP, quantityMap.get(idSP) + quantity);
                } else {
                    quantityMap.put(idSP, quantity);
                }
                hdctRepo.save(hdct);
            }
            for (Map.Entry<Integer, Integer> entry : quantityMap.entrySet()) {
                int idSP = entry.getKey();
                int quantity = entry.getValue();
                SanPhamChiTiet spct = ctspRepo.findById(idSP).get();
                spct.setSoLuongTon(spct.getSoLuongTon() + quantity);
                ctspRepo.save(spct);
            }
            return hdRepo.getByIds(hd.getId());
        } else {
            return null;
        }
    }

    @Override
    public KHHoaDonResponse findById(Integer idHD) {
        return hdRepo.getByIds(idHD);
    }

    @Override
    public KHHoaDonResponse findByMaHD(String maHD) {
        return hdRepo.getByMaHD(maHD);
    }


}
