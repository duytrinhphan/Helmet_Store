package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.khachHang.model.response.DetailSanPhamResponse;
import com.example.demo.core.khachHang.model.response.MauSacResponse;
import com.example.demo.core.khachHang.repository.DetailSPCTTRepository;
import com.example.demo.core.khachHang.service.KHDetailService.DetailSizeService;
import com.example.demo.core.khachHang.service.KHDetailService.ImageServie;
import com.example.demo.core.khachHang.service.KHDetailService.DetaiService;
import com.example.demo.core.khachHang.service.KHDetailService.DetailMauSacService;
import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/khach-hang/detail")
@CrossOrigin(origins = {"*"})
public class Detail {

    @Autowired
    private DetaiService detaiService;

    @Autowired
    private DetailMauSacService mauSacService;

    @Autowired
    private DetailSizeService sizeService;

    @Autowired
    private ImageServie servie;

    @Autowired
    DetailSPCTTRepository detailRepo;

//    @Autowired
//    KHSizeCTRepository sizeCTRepo;
//
//    @Autowired
//    KHMauSacCTRepository mausacCTRepo;


    @GetMapping("/{idctsp}")
    public DetailSanPhamResponse getDetailCTSP(@PathVariable("idctsp") Integer idctsp) {
        DetailSanPhamResponse sanPhamChiTiet = detailRepo.getDetailCTSP(idctsp);
        return sanPhamChiTiet;
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer idctsp) {
        return ResponseEntity.ok(detaiService.findById(idctsp));
    }


    @GetMapping("/find-spct-id")
    public ResponseEntity<?> getSanPhamChiTietBySizeAndMauSac(@RequestParam Integer idMau,@RequestParam(required = false) String idSize,@RequestParam Integer idSP) {
        return ResponseEntity.ok(detaiService.getSanPhamChiTietBySizeAndMauSac(idMau,idSize,idSP));
    }


    @GetMapping("/find-spct-by-idSP/{id}")
    public ResponseEntity<?> getSpctByIdSp(@PathVariable("id") Integer idctsp) {
        return ResponseEntity.ok(detaiService.getAllByIdSp(idctsp));
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        AdminSanPhamChiTietResponse sanPhamChiTiet = detaiService.get(id);
        return ResponseEntity.ok(sanPhamChiTiet);
    }

    @GetMapping("/find-Mau-Sac/{idctsp}")
    public ResponseEntity<?> listMauSacCT(@PathVariable("idctsp") Integer idctsp){
        return ResponseEntity.ok(detaiService.findMauSac(idctsp));
    };
//
    @GetMapping("/find-size/{idctsp}")
    public ResponseEntity<?> findBySize(@PathVariable Integer idctsp){
        return ResponseEntity.ok(detaiService.findSize(idctsp));
    }

    @GetMapping("/findByImage/{id}")
    public ResponseEntity<?> findByImage(@PathVariable Integer id) {

        return ResponseEntity.ok(detaiService.findImage(id));

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getList() {
        List<SanPhamChiTiet> lisst = detaiService.getAlls();
        return ResponseEntity.ok(lisst);
    }

    @GetMapping("/getSLTon/{idctsp}")
    public ResponseEntity<?> getSLTon(@PathVariable("idctsp") Integer idctsp) {
        return ResponseEntity.ok(detailRepo.getSLTonTongByIDCT(idctsp));
    }

    @GetMapping("/getSizeByMS/{idctsp}")
    public ResponseEntity<?> gettListSizeByMauSac(@PathVariable("idctsp") Integer idctsp, @RequestParam("idms") Integer idms) {

        return ResponseEntity.ok(detaiService.findSizeByMauSac(idms, idctsp));
    }


    @GetMapping("/getMauSacBySize/{idctsp}")
    public ResponseEntity<?> getListMauSacBySize(@PathVariable("idctsp") Integer idctsp, @RequestParam("idsizect") String idsizect) {

        return ResponseEntity.ok(detaiService.findMauSacBySize(idsizect, idctsp));
    }


    @GetMapping("/getSanPhamSelected/{idctsp}")
    public ResponseEntity<?> getSanPhamSelected(@PathVariable("idctsp") Integer idctsp, @RequestParam("idms") Integer idms, @RequestParam("idsizect") Integer idsizect) {

        return ResponseEntity.ok(detailRepo.getSanPhamSelected(idctsp, idms, idsizect));
    }
}