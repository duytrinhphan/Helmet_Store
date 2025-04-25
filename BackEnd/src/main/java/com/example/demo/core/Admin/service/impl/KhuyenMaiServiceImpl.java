package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminKhuyenMaiRequest;
import com.example.demo.core.Admin.model.response.AdminKhuyenMaiResponse;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdKhuyenMaiReponsitory;
import com.example.demo.core.Admin.service.AdKhuyenMaiService;
import com.example.demo.entity.KhuyenMai;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.util.DataUltil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@EnableScheduling
public class KhuyenMaiServiceImpl implements AdKhuyenMaiService {

    @Autowired
    AdKhuyenMaiReponsitory khuyenMaiRepo;

    @Autowired
    AdChiTietSanPhamReponsitory chiTietSanPhamReponsitory;

    @Override
    public List<AdminKhuyenMaiResponse> getAllKhuyenMai() {
        return khuyenMaiRepo.getKhuyenMaiByTrangThai();
    }

    @Override
    public HashMap<String, Object> add(AdminKhuyenMaiRequest dto) {
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        KhuyenMai khuyenMai = dto.dtoToEntity(new KhuyenMai());
        LocalDateTime thoiGianHienTai = LocalDateTime.now();
        try {
            if (khuyenMai.getThoiGianKetThuc().isBefore(thoiGianHienTai)) {
                khuyenMai.setTrangThai(1);
            } else if (khuyenMai.getThoiGianBatDau().isAfter(thoiGianHienTai)) {
                khuyenMai.setTrangThai(2);
            } else {
                khuyenMai.setTrangThai(0);
            }
            khuyenMai.setMa("KM"+randomNumber);
            KhuyenMai khuyenmais = khuyenMaiRepo.save(khuyenMai);
            return DataUltil.setData("success", khuyenmais);
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(AdminKhuyenMaiRequest dto, Integer id) {
        Optional<KhuyenMai> optional = khuyenMaiRepo.findById(id);


//        String pattern = "yyyy/MM/dd HH:mm:ss";
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//        LocalDateTime thoiGianBatDau = LocalDateTime.parse(dto.getThoiGianBatDau(), formatter);
//        LocalDateTime thoiGianKetThuc = LocalDateTime.parse(dto.getThoiGianKetThuc(), formatter);

        LocalDateTime thoiGianHienTai = LocalDateTime.now();
        if (optional.isPresent()) {
            KhuyenMai khuyenMai = optional.get();
            khuyenMai.setTen(dto.getTen());
            khuyenMai.setMoTa(dto.getMoTa());
            khuyenMai.setThoiGianBatDau(dto.getThoiGianBatDau());
            khuyenMai.setThoiGianKetThuc(dto.getThoiGianKetThuc());
            khuyenMai.setGiaTriGiam(dto.getGiaTriGiam());
            if (khuyenMai.getThoiGianKetThuc().isBefore(thoiGianHienTai)) {
                khuyenMai.setTrangThai(1);
            } else if (khuyenMai.getThoiGianBatDau().isAfter(thoiGianHienTai)) {
                khuyenMai.setTrangThai(2);
            } else {
                khuyenMai.setTrangThai(0);
            }

            try {
                khuyenMaiRepo.save(khuyenMai);
                return DataUltil.setData("success", khuyenMaiRepo.save(khuyenMai));
            } catch (Exception e) {
                log.info("loi {}",e);
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy khuyến mại để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(AdminKhuyenMaiRequest dto, Integer id) {
        Optional<KhuyenMai> optional = khuyenMaiRepo.findById(id);

//        String pattern = "yyyy/MM/dd HH:mm:ss";
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//        LocalDateTime thoiGianBatDau = LocalDateTime.parse(dto.getThoiGianBatDau(), formatter);
//        LocalDateTime thoiGianKetThuc = LocalDateTime.parse(dto.getThoiGianKetThuc(), formatter);

        if (optional.isPresent()) {
            KhuyenMai khuyenMai = optional.get();
            khuyenMai.setTen(dto.getTen());
            khuyenMai.setMoTa(dto.getMoTa());
            khuyenMai.setThoiGianBatDau(dto.getThoiGianBatDau());
            khuyenMai.setThoiGianKetThuc(dto.getThoiGianKetThuc());
            khuyenMai.setGiaTriGiam(dto.getGiaTriGiam());
            khuyenMai.setTrangThai(4);
            try {
                khuyenMaiRepo.save(khuyenMai);
                return DataUltil.setData("success", khuyenMaiRepo.save(khuyenMai));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy khuyến mại để xoá");
        }
    }


    @Override
    public KhuyenMai getKhuyenMaiById(Integer id) {
        return khuyenMaiRepo.getOneById(id);
    }

    // lấy danh sách ctsp có idkm = null hoặc trạng thái khuyến mại không phải là đang diễn ra hoặc chưa bắt đầu
    @Override
    public List<SanPhamChiTiet> getAllSPCTByKhuyenMai() {
        return khuyenMaiRepo.getAllCTSPByKhuyenMai();

    }

    @Override
    public HashMap<String, Object> updateProductDetail(Integer productId, Integer idkm) {
        // Lấy ctsp theo id
        Optional<SanPhamChiTiet> optionalProductDetail = chiTietSanPhamReponsitory.findById(productId);

        if (optionalProductDetail.isPresent()) {
            SanPhamChiTiet spct = optionalProductDetail.get();
            KhuyenMai km = khuyenMaiRepo.getOneById(idkm);
            // Cập nhật idkm cho ctsp
            spct.setKhuyenMai(km);

            // Áp dụng giảm giá theo giá phần trăm
            if (km.getTrangThai() == 0) {
                BigDecimal giaBan = spct.getGiaBan();

                BigDecimal phanTram = new BigDecimal(km.getGiaTriGiam()).divide(new BigDecimal(100));

                BigDecimal giamGia = giaBan.multiply(phanTram);
                BigDecimal giaBanSauGiam = giaBan.subtract(giamGia);

                spct.setGiaSauGiam(giaBanSauGiam);
            }

            if (km.getTrangThai() == 1) {
                spct.setGiaSauGiam(null);
            }

            try {
                chiTietSanPhamReponsitory.save(spct);
                return DataUltil.setData("success", chiTietSanPhamReponsitory.save(spct));

            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            throw new RuntimeException("Không tìm thấy chi tiết sản phẩm với ID: " + productId);
        }

    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateGiaCTSP() {
        //Lấy danh sách CTSP theo trạng thái khuyến mại là  bắt đầu
        List<SanPhamChiTiet> listSPCT = khuyenMaiRepo.getCTSPByTrangThaiKhuyenMai(0);

        // Set lại giá sau giảm khi trạng thái chuyển từ chưa bắt đầu => đang diễn ra
        for (SanPhamChiTiet spct : listSPCT) {
            KhuyenMai km = khuyenMaiRepo.getOneById(spct.getKhuyenMai().getId());
            BigDecimal giaBan = spct.getGiaBan();
            BigDecimal phanTram = new BigDecimal(km.getGiaTriGiam()).divide(new BigDecimal(100));

            BigDecimal giamGia = giaBan.multiply(phanTram);
            BigDecimal giaBanSauGiam = giaBan.subtract(giamGia);
            spct.setGiaSauGiam(giaBanSauGiam);

            chiTietSanPhamReponsitory.save(spct);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateGiaCTSPHetHan() {
        //Lấy danh sách CTSP theo trạng thái khuyến mại là  hết hạn
        List<SanPhamChiTiet> listCTSPKM = khuyenMaiRepo.getCTSPByTrangThaiKhuyenMai(1);
        // Set lại giá sau giảm khi trạng thái chuyển từ chưa bắt đầu => đang diễn ra và idKM = null
        for(SanPhamChiTiet spct : listCTSPKM){
            KhuyenMai km = khuyenMaiRepo.getOneById(spct.getKhuyenMai().getId());
            spct.setGiaSauGiam(null);
            spct.setKhuyenMai(null);
            chiTietSanPhamReponsitory.save(spct);
        }
    }


    @Scheduled(cron = "0 0 0 * * ?") // Lịch chạy hàng ngày vào lúc 00:00:00
    public void updateNgayHetHan() {
        List<KhuyenMai> khuyenMais = khuyenMaiRepo.findKhuyenMaiByHetHan();
        for (KhuyenMai khuyenMai : khuyenMais) {
            System.out.println(khuyenMai.getId());
            khuyenMai.setTrangThai(1);
            khuyenMaiRepo.save(khuyenMai);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")// Lịch chạy hàng ngày vào lúc 00:00:00
    public void updateNgayChuaBatDau() {
        List<KhuyenMai> khuyenMais = khuyenMaiRepo.findKhuyenMaiByChuaBatDau();
        for (KhuyenMai khuyenMai : khuyenMais) {
            khuyenMai.setTrangThai(2);
            khuyenMaiRepo.save(khuyenMai);
        }


    }

    @Scheduled(cron = "0 0 0 * * *") // Lịch chạy hàng ngày vào lúc 00:00:00
    public void updateNgayConHan() {
        List<KhuyenMai> khuyenMais = khuyenMaiRepo.findKhuyenMaiByConHan();
        for (KhuyenMai khuyenMai : khuyenMais) {
            khuyenMai.setTrangThai(0);
            khuyenMaiRepo.save(khuyenMai);

        }
    }

}
