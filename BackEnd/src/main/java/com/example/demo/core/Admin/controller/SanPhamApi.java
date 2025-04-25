package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.*;
import com.example.demo.core.Admin.model.response.AdminImageResponse;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTiet2Response;
import com.example.demo.core.Admin.model.response.AdminSanPhamResponse;
import com.example.demo.core.Admin.model.response.SanPhamDOT;
import com.example.demo.core.Admin.service.AdSanPhamService.AdExcelAddSanPhamService;
import com.example.demo.core.Admin.service.AdSanPhamService.AdSanPhamService;
import com.example.demo.core.Admin.service.AdSanPhamService.AdUpdateSanPhamService;
import com.example.demo.entity.SanPham;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/admin/san-pham")
public class SanPhamApi {
    @Autowired
    private AdSanPhamService sanPhamService;

    @Autowired
    private AdExcelAddSanPhamService adExcelAddSanPhamService;

    @Autowired
    private AdUpdateSanPhamService adUpdateSanPhamService;

    // getAll loai
//    @GetMapping()
//    public ResponseEntity<?> getAll() {
//        List<AdminSanPhamResponse> page = sanPhamService.getAll();
//        return ResponseEntity.ok(page);
//    }

    @GetMapping("")
    public ResponseEntity<?> tesst() {
        List<SanPhamDOT> page = sanPhamService.getAlls();
        return ResponseEntity.ok(page);
    }


    @GetMapping("/by-loai")
    public ResponseEntity<?> getSPByLoai(@RequestParam("idloai") Integer idloai) {
        List<AdminSanPhamResponse> page = sanPhamService.getSanPhamByIdLoai(idloai);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/by-thuong-hieu")
    public ResponseEntity<?> getSPByThuongHieu(@RequestParam("idthuonghieu") Integer idthuonghieu) {
        List<AdminSanPhamResponse> page = sanPhamService.getSanPhamByIdThuongHieu(idthuonghieu);
        return ResponseEntity.ok(page);
    }

    // thêm bằng file excel
    @PostMapping("/view-data")
    public ResponseEntity<?> viewDataImportExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(adExcelAddSanPhamService.previewDataImportExcel(file));
    }

    @GetMapping("/{idSP}")
    public ResponseEntity<?> getbySanPhamCT(@PathVariable Integer idSP) {
        List<AdminSanPhamChiTiet2Response> page = sanPhamService.findBySanPhamCT(idSP);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{idSP}/images")
    public ResponseEntity<?> getbyImage(@PathVariable Integer idSP) {
        List<AdminImageResponse> page = sanPhamService.getProductImages(idSP);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/loc")
    public ResponseEntity<?> loc(@RequestParam String comboBoxValue) {
        List<SanPhamDOT> lisst = sanPhamService.loc(comboBoxValue);
        return ResponseEntity.ok(lisst);
    }

    @PutMapping("/check")
    public ResponseEntity<?> check(@RequestBody AdminSanPhamRepuest2 request) {
        return ResponseEntity.ok(sanPhamService.findBySanPhamTen(request));
    }

    @PutMapping("/check-spct/{idSize}")
    public ResponseEntity<?> check(@PathVariable Integer idSize, @RequestParam Integer idMau, @RequestParam Integer idSP) {
        return ResponseEntity.ok(adUpdateSanPhamService.check(idSize, idMau, idSP));
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody AdminSanPhamRepuest2 request) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
        SanPhamDOT save = sanPhamService.save(request);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        SanPhamDOT sp = sanPhamService.delete(id);
        return ResponseEntity.ok(sp);
    }

    @PutMapping("/khoi-phuc/{id}")
    public ResponseEntity<?> khoiPhuc(@PathVariable Integer id) {
        SanPhamDOT sp = sanPhamService.khoiPhuc(id);
        return ResponseEntity.ok(sp);
    }


    @PutMapping("/update-san-pham/{id}")
    public ResponseEntity<?> updateSanPham(@PathVariable Integer id, @RequestBody AdminSanPhamRequest sanPhamRequest) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        SanPhamDOT sp = adUpdateSanPhamService.updateSanPham(id, sanPhamRequest);
        return ResponseEntity.ok(sp);
    }

    @PutMapping("/update-san-pham-CT/{id}")
    public ResponseEntity<?> updateSanPhamCT(@PathVariable Integer id, @RequestBody AdminSanPhamChiTietRequest sanPhamRequest) throws IOException, StorageException, InvalidKeyException, URISyntaxException, ExecutionException, InterruptedException {
        AdminSanPhamChiTiet2Response sp = adUpdateSanPhamService.update(sanPhamRequest, id);
        return ResponseEntity.ok(sp);
    }

    @PostMapping("/add-spct")
    public ResponseEntity<?> add(@RequestBody AdminSanPhamChiTietRequest request) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
        AdminSanPhamChiTiet2Response save = adUpdateSanPhamService.saveSanPhamChiTiet(request);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/delete-spct/{id}")
    public ResponseEntity<?> deleteSpct(@PathVariable Integer id) {
        AdminSanPhamChiTiet2Response sp = adUpdateSanPhamService.delete(id);
        return ResponseEntity.ok(sp);
    }

    @PutMapping("/khoi-phuc-spct/{id}")
    public ResponseEntity<?> khoiPhucSPCT(@PathVariable Integer id) {
        AdminSanPhamChiTiet2Response sp = adUpdateSanPhamService.khoiPhuc(id);
        return ResponseEntity.ok(sp);
    }

    @PostMapping("/add-img/{idSP}")
    public ResponseEntity<?> addImg(@PathVariable Integer idSP, @RequestBody AdminAddImageRequest adminAddImageRequest) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
        AdminImageResponse save = adUpdateSanPhamService.saveImage(idSP, adminAddImageRequest);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/delete-img/{idSP}")
    public ResponseEntity<?> deleteImg(@PathVariable Integer idSP) {
        AdminImageResponse sp = adUpdateSanPhamService.deleteImg(idSP);
        return ResponseEntity.ok(sp);
    }

    @PutMapping("/update-img/{id}")
    public ResponseEntity<?> updateImg(@PathVariable Integer id, @RequestBody AdminAddImageRequest dto) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        AdminImageResponse sp = adUpdateSanPhamService.updateImage(id, dto);
        return ResponseEntity.ok(sp);
    }

}
