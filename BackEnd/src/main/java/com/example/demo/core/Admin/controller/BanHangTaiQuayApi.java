package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.BHTQHoaDonRequest;
import com.example.demo.core.Admin.model.request.BHTQUserRequest;
import com.example.demo.core.Admin.model.response.BHTQUserResponse;
import com.example.demo.core.Admin.service.BanHangTaiQuayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/ban-hang-tai-quay")
public class BanHangTaiQuayApi {
    private final BanHangTaiQuayService service;

    public BanHangTaiQuayApi(BanHangTaiQuayService service) {
        this.service = service;
    }

    @GetMapping("/hoa-don")
    public ResponseEntity<?> getAllHDCho() {
        return ResponseEntity.ok(service.getAllHDCho());
    }

    @PostMapping("/hoa-don")
    public ResponseEntity<?> taoHDCho(@RequestParam("id_nv") Integer idNV, @RequestParam(value = "id_kh", required = false) Integer idKH) {
        service.taoHDCho(idNV, idKH);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.getAllHDCho());
    }

    @PutMapping("/hoa-don/{idHoaDon}")
    public ResponseEntity<?> thanhToanHD(@PathVariable("idHoaDon") Integer idHD, @RequestBody BHTQHoaDonRequest dto) {
        service.thanhToanHD(idHD, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/hoa-don/{idHoaDon}/khach-hang")
    public ResponseEntity<?> updateKHForHD(@PathVariable("idHoaDon") Integer idHD, @RequestParam("id_kh") Integer idKH) {
        service.updateKHForHD(idHD, idKH);
        return ResponseEntity.ok(service.getAllHDCho());
    }

    @DeleteMapping("/hoa-don/{idHoaDon}")
    public ResponseEntity<?> huyHDCho(@PathVariable Integer idHoaDon) {
        service.huyHDCho(idHoaDon);
        return ResponseEntity.ok(service.getAllCTSP());
    }

    @GetMapping("/hoa-don-chi-tiet")
    public ResponseEntity<?> getAllHDCT(@RequestParam("id_hd") Integer idHoaDon) {
        return ResponseEntity.ok(service.getAllHDCT(idHoaDon));
    }

    @PostMapping("/hoa-don-chi-tiet")
    public ResponseEntity<?> addSPToHDCT(@RequestParam("id_hd") Integer idHoaDon,
                                         @RequestBody Map<String, Integer> reqBody) {
        service.addSPToHDCT(idHoaDon, reqBody.get("idCTSP"), reqBody.get("soLuong"));
        Map<String, Object> response = new HashMap<>();
        response.put("allHDCT", service.getAllHDCT(idHoaDon));
        response.put("allSP", service.getAllCTSP());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/hoa-don-chi-tiet/{idHDCT}")
    public ResponseEntity<?> updateSLSPCuaHDCT(@PathVariable("idHDCT") Integer idHDCT,
                                               @RequestParam("so_luong") Integer soLuong) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("allHDCT", service.updateSLSPCuaHDCT(idHDCT, soLuong));
        resp.put("allCTSP", service.getAllCTSP());
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/hoa-don-chi-tiet/{idHDCT}")
    public ResponseEntity<?> deleteHDCT(@PathVariable("idHDCT") Integer idHDCT) {
        service.deleteHDCT(idHDCT);
        return ResponseEntity.ok(service.getAllCTSP());
    }

    @GetMapping("/san-pham")
    public ResponseEntity<?> getAllCTSP() {
        return ResponseEntity.ok(service.getAllCTSP());
    }

    @GetMapping("/phuong-thuc-thanh-toan")
    public ResponseEntity<?> getAllPTTT() {
        return ResponseEntity.ok(service.getAllPTTT());
    }

    @GetMapping("/khach-hang")
    public ResponseEntity<?> getAllKH() {
        return ResponseEntity.ok(service.getAllKH());
    }

    @PostMapping("/khach-hang")
    public ResponseEntity<?> addOrUpdateKH(@RequestBody BHTQUserRequest dto) {
        BHTQUserResponse resp = service.addOrUpdateKH(dto);
        if (resp.getMessage() != null) return ResponseEntity.status(HttpStatus.CONFLICT).body(resp.getMessage());
        if (dto.getId() == null) return ResponseEntity.ok(resp);
        return ResponseEntity.ok(service.getAllKH());
    }

    @PostMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().build();
    }
}
