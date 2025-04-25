package com.example.demo.infrastructure.exportPdf;

import com.example.demo.core.khachHang.model.response.BienLaiHoaDon;
import com.example.demo.core.khachHang.model.response.HDCTRespon;
import com.example.demo.core.khachHang.model.response.KHHoaDonChiTietResponse;
import com.example.demo.core.khachHang.repository.KHDiaChiRepository;
import com.example.demo.core.khachHang.repository.KHHoaDonChiTietRepository;
import com.example.demo.core.khachHang.repository.KHVoucherRepository;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.Voucher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ExportFilePdfFormHtml {

    @Autowired
    KHHoaDonChiTietRepository hdctRepo ;

    @Autowired
    KHDiaChiRepository khDiaChiRepo;

    @Autowired
    KHVoucherRepository khVoucherRepo;

    public Context setDataSendMail(BienLaiHoaDon invoice, String url) {

        Context context = new Context();

        Map<String, Object> data = new HashMap<>();

        data.put("invoice", invoice);
        data.put("url", url);
        context.setVariables(data);

        return context;
    }

    public  NumberFormat formatCurrency() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
        formatter.setCurrency(Currency.getInstance("VND"));
        return formatter;
    }
    public BienLaiHoaDon getInvoiceResponse(HoaDon hoaDon) {

        List<KHHoaDonChiTietResponse> billDetailResponses = hdctRepo.findHDCTByIDHD(hoaDon.getId());
        NumberFormat formatter = formatCurrency();
        DiaChi diaChi = khDiaChiRepo.findAllById(hoaDon.getDiaChi().getId());


           // Voucher voucher = khVoucherRepo.findAllById(hoaDon.getVoucher().getId()).get();
          //  BigDecimal giaGiam = hoaDon.getTongTien().multiply(BigDecimal.valueOf(voucher.getGiaTriGiam()).divide(BigDecimal.valueOf(100)));

        String giamGiaText;
        BigDecimal giaGiam;

        if (hoaDon.getVoucher()!= null) {
            Voucher voucher = khVoucherRepo.findAllById(hoaDon.getVoucher().getId()).get();
            BigDecimal giaTriGiam = BigDecimal.valueOf(voucher.getGiaTriGiam());

         if (giaTriGiam.compareTo(BigDecimal.ZERO) > 0) {


                // Giảm giá theo phần trăm
                giaGiam = hoaDon.getTongTien().multiply(giaTriGiam.divide(BigDecimal.valueOf(100)));

                if(giaGiam.compareTo(hoaDon.getVoucher().getGiamToiDa()) > 0){
                    giaGiam = hoaDon.getVoucher().getGiamToiDa();
                }
                giamGiaText = formatter.format(giaGiam) + " - " + giaTriGiam + "%";
            } else {

                giamGiaText = "Không có giảm giá";
            }
        } else {

            giamGiaText = "Không có giảm giá";
        }


        if(hoaDon.getTienSauKhiGiam() == null || hoaDon.getTienSauKhiGiam().equals("")){
            hoaDon.setTienSauKhiGiam(hoaDon.getTienShip().add(hoaDon.getTongTien()));
        }

        BienLaiHoaDon invoice = BienLaiHoaDon.builder()
                .sdt(hoaDon.getUser().getSdt())
                .diaChi(diaChi.getDiaChi()+", "+diaChi.getTenphuongXa()+", "+diaChi.getTenQuanHuyen() +", "+diaChi.getTenTinhThanh())
                .ten(hoaDon.getUser().getTen())
                .ma(hoaDon.getMa())
                .phiShip(formatter.format(hoaDon.getTienShip()))
                .tongTien(formatter.format(hoaDon.getTongTien()))
                .giamgia(giamGiaText )
                .tongThanhToan(formatter.format(hoaDon.getTongTien().add(hoaDon.getTienShip())))
                .date(hoaDon.getNgayThanhToan())
                .build();

        if (hoaDon.getVoucher()== null) {

            invoice.setTongThanhToan(formatter.format(hoaDon.getTongTien().add(hoaDon.getTienShip())));
        }else{
            invoice.setTongThanhToan(formatter.format(hoaDon.getTienSauKhiGiam()));
        }

        List<HDCTRespon> items = billDetailResponses.stream()
                .map(billDetailRequest -> {

                    HDCTRespon invoiceItemResponse = HDCTRespon.builder()
                            .ma(billDetailRequest.getTenSP())
                            .donGia(billDetailRequest.getDonGia())
                            .soLuong(billDetailRequest.getSoLuong())
                            .build();

                    return invoiceItemResponse;
                })
                .collect(Collectors.toList());

        invoice.setItems(items);

        return invoice;
    }
}
