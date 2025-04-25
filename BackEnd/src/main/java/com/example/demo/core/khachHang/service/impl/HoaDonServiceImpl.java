package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.service.EmailSenderService;
import com.example.demo.core.khachHang.model.request.hoadon.HoaDonRequest;
import com.example.demo.core.khachHang.model.request.hoadonchitiet.KHHoaDonChiTietRequest;

import com.example.demo.core.khachHang.model.response.BienLaiHoaDon;
import com.example.demo.core.khachHang.repository.*;
import com.example.demo.core.khachHang.service.HoaDonService;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.constant.VNPayConstant;
import com.example.demo.infrastructure.exportPdf.ExportFilePdfFormHtml;

import com.example.demo.infrastructure.sendmail.SendEmailService;
import com.example.demo.infrastructure.status.ChiTietSanPhamStatus;
import com.example.demo.infrastructure.status.HinhThucGiaoHangStatus;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
public class HoaDonServiceImpl implements HoaDonService {
    @Value("${frontend.base-endpoint}")
    private static String BASE_FRONTEND_ENDPOINT;

    @Autowired
    KHUserRepository khUserRepo;

    @Autowired
    KHDiaChiRepository khDiaChiRepo;

    @Autowired
    KHHoaDonRepository khHoaDonRepo;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepo;

    @Autowired
    KHChiTietSPRepository chiTietSPRepo;

    @Autowired
    KHVoucherRepository voucherRepo;

    @Autowired
    KHGioHangRepository khGioHangRepo;

    @Autowired
    KHGioHangChiTietRepository khghctRepo;

    @Autowired
    ExportFilePdfFormHtml exportFilePdfFormHtml;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    private VNPayConstant VnPayConstant;

    @Autowired
    HoaDonRepository hoaDonRepo;

    @Autowired
    EmailSenderService sendEmailService;

    @Autowired
    private ThongBaoServiceImpl thongBaoService;

    @Override
    public HoaDon createHoaDon(HoaDonRequest hoaDonRequest) {

        User kh = this.khUserRepo.findById(hoaDonRequest.getIdUser()).get();
        DiaChi diaChi = khDiaChiRepo.findDiaChiByIdUserAndTrangThai(kh.getId());



        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        LocalDateTime ngayThanhToan;

        if (hoaDonRequest.getIdPayMethod() == 2) {
            ngayThanhToan = DatetimeUtil.getCurrentDateAndTimeLocal();
        } else {
            ngayThanhToan = null;
        }

        BigDecimal tienSauGiam;
        if(hoaDonRequest.getTienSauGiam() == null || hoaDonRequest.getTienSauGiam().compareTo(BigDecimal.ZERO) == 0){
            tienSauGiam = hoaDonRequest.getTongTien();
        }else{
            tienSauGiam = hoaDonRequest.getTienSauGiam();
        }


        HoaDon hoaDon = HoaDon.builder()
                .user(kh)
                .diaChi(diaChi)
                .tongTien(hoaDonRequest.getTongTien())
                .tenNguoiNhan(kh.getTen())
                .ngayTao(DatetimeUtil.getCurrentDateAndTimeLocal())
                .tienShip(hoaDonRequest.getTienShip())
                .ngayThanhToan(ngayThanhToan)
                .tienSauKhiGiam(tienSauGiam)
                .hinhThucGiaoHang(HinhThucGiaoHangStatus.GIAOHANG)
                .trangThai(HoaDonStatus.YEU_CAU_XAC_NHAN)
                .phuongThucThanhToan(PhuongThucThanhToan.builder().id(hoaDonRequest.getIdPayMethod()).build())
                .build();

//        hoaDon.setMa("HD".concat(new HoaDon().getId().toString()));

        if(hoaDonRequest.getIdVoucher() != null) {
            Voucher voucher = voucherRepo.findAllById(hoaDonRequest.getIdVoucher()).get();
            hoaDon.setVoucher(voucher);
        }

        HoaDon saveHoaDon = khHoaDonRepo.save(hoaDon);
        saveHoaDon.setMa("HD" + saveHoaDon.getId().toString());
        khHoaDonRepo.save(saveHoaDon);

        if(hoaDonRequest.getIdVoucher() != null) {
            Voucher voucher = voucherRepo.findAllById(hoaDonRequest.getIdVoucher()).get();
            voucher.setSoLuong(voucher.getSoLuong() - 1);
            if(voucher.getSoLuong() == 0){
                voucher.setTrangThai(2);
            }
            voucherRepo.save(voucher);
        }

        for (KHHoaDonChiTietRequest request : hoaDonRequest.getListHDCT()) {

            SanPhamChiTiet spct = chiTietSPRepo.findById(request.getIdCTSP()).get();

            if (spct.getSoLuongTon() < request.getSoLuong()) {
                throw new RuntimeException("So luong khong du");
            }

            HoaDonChiTiet hdct = HoaDonChiTiet.builder()
                    .sanPhamChiTiet(spct)
                    .ma("HDCT" + randomNumber)
                    .soLuong(request.getSoLuong())
                    .donGia(spct.getGiaSauGiam() == null ? spct.getGiaBan() : spct.getGiaSauGiam())
                    .trangThai(HoaDonStatus.YEU_CAU_XAC_NHAN)
                    .hoaDon(hoaDon)
                    .build();

            hoaDonChiTietRepo.save(hdct);

            spct.setSoLuongTon(spct.getSoLuongTon() - request.getSoLuong());

            if (spct.getSoLuongTon() == 0) {
                spct.setTrangThai(ChiTietSanPhamStatus.HET_HANG);
            }

            chiTietSPRepo.save(spct);


        }
        for (KHHoaDonChiTietRequest x : hoaDonRequest.getListHDCT()) {
            GioHangChiTiet gioHangChiTiet = khghctRepo.listGHCTByID(hoaDonRequest.getIdUser(), x.getIdCTSP());
           if(gioHangChiTiet != null){
               khghctRepo.deleteById(gioHangChiTiet.getId());
           }

        }
//        this.updateVoucher(kh.getId(), hoaDonRequest.getIdVoucher());
        this.thongBaoService.thanhToan(saveHoaDon.getId());
        sendMailOnline(hoaDon.getId());
        return hoaDon;
    }

    public void sendMailOnline(Integer idHoaDon) {
        String finalHtml = null;
        HoaDon hoaDon = hoaDonRepo.findAllById(idHoaDon);
        BienLaiHoaDon invoice = exportFilePdfFormHtml.getInvoiceResponse(hoaDon);

        User user = khUserRepo.findAllById(hoaDon.getUser().getId()).get();

        sendMail(invoice, BASE_FRONTEND_ENDPOINT + "/success", user.getEmail());
        //}
    }

    public void sendMail(BienLaiHoaDon invoice, String url, String email) {

        String finalHtmlSendMail = null;
        Context dataContextSendMail = exportFilePdfFormHtml.setDataSendMail(invoice, url);
        finalHtmlSendMail = springTemplateEngine.process("Bill", dataContextSendMail);
        String subject = "Biên lai ";
        sendEmailService.sendSimpleEmail(email, subject, finalHtmlSendMail);

    }
}
