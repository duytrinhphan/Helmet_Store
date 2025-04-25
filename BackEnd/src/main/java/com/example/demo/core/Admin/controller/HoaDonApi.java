package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.service.InterfaceHoaDon.AdDetailHoaDonChiTietService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonChoXacNhanService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonDangGiaoService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonDoiTraService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdminTatCaHoaDonService;
import com.example.demo.core.Admin.service.impl.AdThongBaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/hoaDon")
public class HoaDonApi {

    @Autowired
    private AdminTatCaHoaDonService adminTatCaHoaDonService;

    @Autowired
    private AdHoaDonChoXacNhanService adHoaDonChoXacNhanService;

    @Autowired
    private AdHoaDonDangGiaoService adHoaDonDangGiaoService;

    @Autowired
    private AdDetailHoaDonChiTietService adDetailHoaDonChiTietService;

    @Autowired
    private AdHoaDonDoiTraService doiTraService;

    @Autowired
    private AdThongBaoServiceImpl adThongBaoService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getAll());
    }

    @GetMapping("/find-tat-ca-by-pttt")
    public ResponseEntity<?> getAllByPttt(@RequestParam("pttt") Integer pttt) {
        return ResponseEntity.ok(adminTatCaHoaDonService.getAllByPttt(pttt));
    }

    @GetMapping("/find-tat-ca-by-hinh-thuc-giao")
    public ResponseEntity<?> getAllByHinhThucGiao(@RequestParam("hinhThucGiao") Integer hinhThucGiao) {
        return ResponseEntity.ok(adminTatCaHoaDonService.getAllByHinhThucGiao(hinhThucGiao));
    }

    @GetMapping("/find-tat-ca-by-hinh-thuc-giao-and_pttt")
    public ResponseEntity<?> getAllByHinhThucGiaoAndPttt(@RequestParam("hinhThucGiao") Integer hinhThucGiao, @RequestParam("pttt") Integer pttt) {
        return ResponseEntity.ok(adminTatCaHoaDonService.getAllByHinhThucGiaoAndPttt(hinhThucGiao, pttt));
    }

    @GetMapping("/hoaDonTrangThai/{trangThai}")
    public ResponseEntity<?> getHoaDonHuy(@PathVariable Integer trangThai) {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonTrangThai(trangThai));
    }

    @GetMapping("/hoaDonTrangThai/hinh-thuc-giao/{trangThai}")
    public ResponseEntity<?> getHoaDonByStatusAndHinhThucGiao(@PathVariable Integer trangThai, @RequestParam("hinhThucGiao") Integer hinhThucGiao) {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonTrangThaiAndHinhThucGiao(trangThai, hinhThucGiao));
    }

    @GetMapping("/hoaDonTrangThai/pttt/{trangThai}")
    public ResponseEntity<?> getHoaDonByStatusAndPttt(@PathVariable Integer trangThai, @RequestParam("pttt") Integer pttt) {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonTrangThaiAndPttt(trangThai, pttt));
    }

    @GetMapping("/hoaDonTrangThai/pttt-and-htgh/{trangThai}")
    public ResponseEntity<?> getHoaDonByStatusAndPttt(@PathVariable Integer trangThai, @RequestParam("pttt") Integer pttt,
                                                      @RequestParam("htgh") Integer htgh) {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonTrangThaiAndPtttAndHtgh(trangThai, pttt, htgh));
    }

    @GetMapping("/search-date")
    public ResponseEntity<?> getHoaDonHuy(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam String comboBoxValue) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);
        return ResponseEntity.ok(adminTatCaHoaDonService.searchDate(date, date2, comboBoxValue));
    }

    @GetMapping("/search-date-by-hinh-thuc-giao")
    public ResponseEntity<?> searchHDByHinhThucGiao(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam String comboBoxValue, @RequestParam Integer hinhThucGiao) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);
        return ResponseEntity.ok(adminTatCaHoaDonService.searchDateAndHinhThucGiao(date, date2, comboBoxValue, hinhThucGiao));
    }

    @GetMapping("/search-date-by-pttt")
    public ResponseEntity<?> searchHDByPttt(@RequestParam("startDate") String startDate,
                                            @RequestParam("endDate") String endDate,
                                            @RequestParam String comboBoxValue,
                                            @RequestParam Integer pttt) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);
        return ResponseEntity.ok(adminTatCaHoaDonService.searchDateAndPttt(date, date2, comboBoxValue, pttt));
    }

    @GetMapping("/search-date-by-pttt-and-htgh")
    public ResponseEntity<?> searchHDByPtttAndHtgd(@RequestParam("startDate") String startDate,
                                                   @RequestParam("endDate") String endDate,
                                                   @RequestParam String comboBoxValue,
                                                   @RequestParam Integer pttt,
                                                   @RequestParam Integer htgh) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);
        return ResponseEntity.ok(adminTatCaHoaDonService.searchDateAndPtttAndHtgh(date, date2, comboBoxValue, pttt, htgh));
    }

    @GetMapping("/search-date-by-trang-thai")
    public ResponseEntity<?> getHDByTrangThai(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                                              @RequestParam String comboBoxValue, @RequestParam("trangThai") Integer trangThai) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);
        System.out.println(adminTatCaHoaDonService.searchDateByTrangThai(date, date2, comboBoxValue, trangThai).size());
        return ResponseEntity.ok(adminTatCaHoaDonService.searchDateByTrangThai(date, date2, comboBoxValue, trangThai));
    }

    @GetMapping("/search-date-by-trang-thai-and-hinh-thuc-giao")
    public ResponseEntity<?> getHDByTrangThaiAndHinhThucGiao(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                                                             @RequestParam String comboBoxValue, @RequestParam("trangThai") Integer trangThai,
                                                             @RequestParam Integer hinhThucGiao) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);
        return ResponseEntity.ok(adminTatCaHoaDonService.searchDateByTrangThaiAndHinhThucGiao(date, date2, comboBoxValue, trangThai, hinhThucGiao));
    }

    @GetMapping("/search-date-by-trang-thai-and-pttt")
    public ResponseEntity<?> getHDByTrangThaiAndPttt(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                                                     @RequestParam String comboBoxValue, @RequestParam("trangThai") Integer trangThai,
                                                     @RequestParam Integer pttt) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);
        return ResponseEntity.ok(adminTatCaHoaDonService.searchDateByTrangThaiAndPttt(date, date2, comboBoxValue, trangThai, pttt));
    }

    @GetMapping("/search-date-by-trang-thai-and-pttt-and-htgh")
    public ResponseEntity<?> getHDByTrangThaiAndPtttAndHtgh(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                                                            @RequestParam String comboBoxValue, @RequestParam("trangThai") Integer trangThai,
                                                            @RequestParam Integer pttt, @RequestParam Integer htgh) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);
        return ResponseEntity.ok(adminTatCaHoaDonService.searchDateByTrangThaiAndPtttAndHtgh(date, date2, comboBoxValue, trangThai, pttt, htgh));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detailHoaDonCT(@PathVariable Integer id) {
        return ResponseEntity.ok(adDetailHoaDonChiTietService.getHoaDonChiTietByIdHD(id));
    }

    @GetMapping("/hoaDonHuy")
    public ResponseEntity<?> getHoaDonHuy() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonHuy());
    }

    @GetMapping("/hoaDonXacNhan")
    public ResponseEntity<?> getHoaDonXacNhan() {
        return ResponseEntity.ok(adHoaDonChoXacNhanService.getHoaDonChoXacNhan());
    }

    @GetMapping("/hoaDonHoanThanh")
    public ResponseEntity<?> getHoaDonHoanThanh() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonHoanThanh());
    }

    @GetMapping("/hoaDonDangGiao")
    public ResponseEntity<?> getHoaDonDangGiao() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonDangGiao());
    }

    @GetMapping("/hoaDonYeuCauDoiTra")
    public ResponseEntity<?> getHoaDonYeuCauDoiTra() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonYeuCauDoiTra());
    }

    @GetMapping("/hoaDonXacNhanDoiTra")
    public ResponseEntity<?> getHoaDonXacNhanDoiTra() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonXacNhanDoiTra());
    }

    @GetMapping("/hoaDonDangChuanBiHang")
    public ResponseEntity<?> getHoaDonDangChuanBiHang() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonDangChuanBiHang());
    }

    @PutMapping("/huyXacNhan/{id}")
    public ResponseEntity<?> huyHoaDon(@PathVariable Integer id, @RequestParam("lyDo") String lyDo) {
        adThongBaoService.huyHoaDon(id);
        return ResponseEntity.ok(adHoaDonChoXacNhanService.huyHoaDonChoXacNhan(id, lyDo));
    }

    // từ chờ xác nhận -> đang chuẩn bị
    @PutMapping("/XacNhan/{id}")
    public ResponseEntity<?> xacNhanHoaDon(@PathVariable Integer id) {
        return ResponseEntity.ok(adHoaDonChoXacNhanService.xacNhanHoaDon(id));
    }

    // chuẩn bị xong -> đang giao
    @PutMapping("/XacNhanGiaoHang/{id}")
    public ResponseEntity<?> XacNhanGiaoHang(@PathVariable Integer id, @RequestParam("ngayShip") String ngayShip,
                                             @RequestParam Integer tongTien,@RequestParam Integer tienShip) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(ngayShip, formatter);
        adThongBaoService.xacNhanHoaDon(id);
        return ResponseEntity.ok(adminTatCaHoaDonService.giaoHoaDonChoVanChuyen(id, date,tongTien,tienShip));
    }

    // Trả hàng -> Xác nhận trả
    @PutMapping("/xac-nhan-doi-tra/{id}")
    public ResponseEntity<?> XacNhanDoiTra(@PathVariable Integer id, @RequestParam("idSPCT") Integer idSPCT) {
        adThongBaoService.xacNhanDoiTra(id);
        return ResponseEntity.ok(doiTraService.xacNhanHoaDonTraHang(id, idSPCT));
    }

    @PutMapping("/huy-doi-tra/{id}")
    public ResponseEntity<?> huyHoaDonDoiTra(@PathVariable Integer id, @RequestParam("lyDo") String lyDo, @RequestParam("idSPCT") Integer idSPCT) {
        adThongBaoService.HuyDoiTra(id);
        return ResponseEntity.ok(doiTraService.huyHoaDonTrahang(id, lyDo, idSPCT));
    }

    //Xác nhận trả hàng => hoàn thành trả hang
    @PutMapping("/hoan-thanh-doi-tra/{id}")
    public ResponseEntity<?> hoanThanhDoiTra(@PathVariable Integer id) {
        adThongBaoService.hoanThanhDoiTra(id);
        return ResponseEntity.ok(doiTraService.congSoLuongSP(id));
    }

    //Xác nhận trả hàng => hoàn thành trả hang không cộng lại số lượng
    @PutMapping("/hoan-thanh-tra/{id}")
    public ResponseEntity<?> hoanThanhDoiTraKhongCongSoLuong(@PathVariable Integer id) {
        adThongBaoService.hoanThanhDoiTra(id);
        return ResponseEntity.ok(doiTraService.khongCongSoLuongSP(id));
    }

    // từ đang giao -> hoàn thành
    @PutMapping("/hoan-thanh/{id}")
    public ResponseEntity<?> hoanThanh(@PathVariable Integer id) {
        adThongBaoService.hoanThanh(id);
        return ResponseEntity.ok(adHoaDonDangGiaoService.xacNhanHoaDon(id));
    }

    //tạo nhánh
}
