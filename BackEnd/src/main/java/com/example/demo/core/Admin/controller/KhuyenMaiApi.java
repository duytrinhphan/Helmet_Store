package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminKhuyenMaiRequest;
import com.example.demo.core.Admin.model.response.AdminKhuyenMaiResponse;
import com.example.demo.core.Admin.service.AdKhuyenMaiService;
import com.example.demo.core.Admin.service.impl.AdExcelKhuyenMaiServiceImpl;
import com.example.demo.entity.KhuyenMai;
import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/khuyenMai")
public class KhuyenMaiApi {

    @Autowired
    AdKhuyenMaiService khuyenMaiService;

    @Autowired
    AdExcelKhuyenMaiServiceImpl adExcelKhuyenMaiService;


    @GetMapping("/getAll")
    public List<AdminKhuyenMaiResponse> getAllKhuyenMai() {
        return khuyenMaiService.getAllKhuyenMai();
    }

    @GetMapping("/getById/{id}")
    public KhuyenMai getKhuyenmaiByID(@PathVariable("id") Integer id) {
        return khuyenMaiService.getKhuyenMaiById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AdminKhuyenMaiRequest request) {
        HashMap<String, Object> map = khuyenMaiService.add(request);
        return ResponseEntity.ok(map);
    }

    // sửa
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody AdminKhuyenMaiRequest request) {
        HashMap<String, Object> map = khuyenMaiService.update(request, id);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestBody AdminKhuyenMaiRequest request) {
        HashMap<String, Object> map = khuyenMaiService.delete(request, id);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getAllCTSPByKhuyenMai")
    public List<SanPhamChiTiet> getAllCTSPByKhuyenMai() {

        return khuyenMaiService.getAllSPCTByKhuyenMai();
    }

    // áp dụng khuyến mại cho sản phẩm được chọn
    @PutMapping("/applyKM/{productId}")
    public ResponseEntity<?> updateKM(@PathVariable("productId") Integer productId, @RequestParam("idkm") Integer idkm) {
        HashMap<String, Object> map = khuyenMaiService.updateProductDetail(productId, idkm);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/view-data")
    public ResponseEntity<?> viewDataImportExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(adExcelKhuyenMaiService.previewDataImportExcel(file));
    }
}
