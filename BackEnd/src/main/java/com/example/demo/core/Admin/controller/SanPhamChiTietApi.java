package com.example.demo.core.Admin.controller;


import com.example.demo.core.Admin.model.response.AdminSPCTResponse;
import com.example.demo.core.Admin.service.AdSanPhamChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/ctsp")
//
public class SanPhamChiTietApi {

    @Autowired
    private AdSanPhamChiTietService sanPhamChiTietService;

//    @Autowired
//    private AdUpdateSanPhamService updateSanPhamServiceIpml;
//
//    @Autowired
//    private AdExcelAddSanPhamService adExcelAddSanPhamService;
//
//    @Autowired
//    private AdminMauSacChiTietService adminMauSacChiTietService;
//
//    @Autowired
//    private AdminSizeChiTietService adminSizeChiTietService;
//
//
    @GetMapping()
    public ResponseEntity<?> getList(@RequestParam("idsp") Integer idsp) {
        List<AdminSPCTResponse> lisst = sanPhamChiTietService.listCTSPBySanPham(idsp);
        return ResponseEntity.ok(lisst);
    }
//
//    @GetMapping("/loc")
//    public ResponseEntity<?> loc(@RequestParam String comboBoxValue) {
//        List<AdminSanPhamChiTietResponse> lisst = sanPhamChiTietService.loc(comboBoxValue);
//        return ResponseEntity.ok(lisst);
//    }
//
//
//    @GetMapping("/{productId}/images")
//    public ResponseEntity<?> getProductImages(@PathVariable Integer productId) {
//        List<Image> imageUrls = sanPhamChiTietService.getProductImages(productId);
//        return ResponseEntity.ok(imageUrls);
//    }
//
//    @GetMapping("/images")
//    public ResponseEntity<?> getProductImages() {
//        List<Image> imageUrls = sanPhamChiTietService.getProductImages();
//        return ResponseEntity.ok(imageUrls);
//    }
//
//    @GetMapping("/{productId}/size")
//    public ResponseEntity<?> getProductSize(@PathVariable Integer productId) {
//        List<AdminSizeChiTietResponse> size = sanPhamChiTietService.getProductSize(productId);
//        return ResponseEntity.ok(size);
//    }
//
//    @GetMapping("/{productId}/mauSac")
//    public ResponseEntity<?> getProductMauSac(@PathVariable Integer productId) {
//        List<AdminMauSacChiTietResponse> mauSac = sanPhamChiTietService.getProductMauSac(productId);
//        return ResponseEntity.ok(mauSac);
//    }
//
//    @GetMapping("/getOne/{id}")
//    public ResponseEntity<?> getOne(@PathVariable Integer id) {
//        AdminSanPhamChiTietResponse sanPhamChiTiet = sanPhamChiTietService.get(id);
//        return ResponseEntity.ok(sanPhamChiTiet);
//    }
//
//    @GetMapping("/check/{ten}")
//    public ResponseEntity<?> check(@PathVariable String ten) {
//        return ResponseEntity.ok(sanPhamChiTietService.findBySanPhamTen(ten));
//    }
//
//    @DeleteMapping("/deleteSize")
//    public void deleteSize(@RequestParam Integer idSP, @RequestParam Integer idSize) {
//        updateSanPhamServiceIpml.deleteSize(idSP, idSize);
//    }
//
//    @DeleteMapping("/deleteMauSac/{idMau}")
//    public void deleteMauSac(@PathVariable Integer idMau) {
//        updateSanPhamServiceIpml.deleteMauSac(idMau);
//    }
//
//    @DeleteMapping("/deleteImg")
//    public void deleteImg(@RequestParam Integer idSP, @RequestParam String img) {
//        updateSanPhamServiceIpml.deleteImg(idSP, img);
//    }
//
//    // thêm bằng file excel
//    @PostMapping("/view-data")
//    public ResponseEntity<?> viewDataImportExcel(@RequestParam("file") MultipartFile file) throws IOException {
//        return ResponseEntity.ok(adExcelAddSanPhamService.previewDataImportExcel(file));
//    }
//
//    @PostMapping()
//    public ResponseEntity<?> adds(@Valid @RequestBody AdminSanPhamChiTietRequest sanPhamChiTietRequest
//            , BindingResult result) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
//        if (result.hasErrors()) {
//            List<ObjectError> list = result.getAllErrors();
//            return ResponseEntity.ok(DataUltil.setData("error", list));
//        } else {
//            return ResponseEntity.ok(sanPhamChiTietService.add(sanPhamChiTietRequest));
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@Valid @RequestBody AdminSanPhamChiTietRequest sanPhamChiTietRequest, @PathVariable Integer id
//            , BindingResult result) throws IOException, StorageException, InvalidKeyException, URISyntaxException, ExecutionException, InterruptedException {
//        if (result.hasErrors()) {
//            List<ObjectError> list = result.getAllErrors();
//            return ResponseEntity.ok(DataUltil.setData("error", list));
//        } else {
//            return ResponseEntity.ok(updateSanPhamServiceIpml.update(sanPhamChiTietRequest, id));
//        }
//    }
//
//    @PutMapping("/{id}/delete")
//    public ResponseEntity<?> delete(@PathVariable Integer id) {
//        return ResponseEntity.ok(updateSanPhamServiceIpml.delete(id));
//    }
//
//    @PostMapping("/add-mau-sac-chi-tiet")
//    public ResponseEntity<?> addMauSacChiTiet(@RequestBody AdminMauSacChiTietRequest request) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
//        return ResponseEntity.ok(adminMauSacChiTietService.add(request));
//    }
//
//    @GetMapping("/checkMauSac")
//    public ResponseEntity<?> checkMauSac(@RequestParam Integer idMauSac, @RequestParam Integer idSP, @RequestParam(required = false) Integer idSizeCT) {
////        if (idSizeCT == null) {
////            return ResponseEntity.ok(adminMauSacChiTietService.check(idSP, idMauSac));
////        } else
//            return ResponseEntity.ok(adminMauSacChiTietService.checks(idSP, idMauSac, idSizeCT));
//    }
//
//    @PutMapping("/update-mau-sac-chi-tiet/{id}")
//    public ResponseEntity<?> updateMauSacChiTiet(@PathVariable Integer id, @RequestBody AdminMauSacChiTietRequest request) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
//        return ResponseEntity.ok(adminMauSacChiTietService.update(id, request));
//    }
//
//    @DeleteMapping("/delete-mau-sac-chi-tiet/{id}")
//    public ResponseEntity<?> deleteMauSacChiTiet(@PathVariable Integer id) {
//        adminMauSacChiTietService.delete(id);
//        return ResponseEntity.ok("");
//    }
//
//    @PostMapping("/add-size-chi-tiet")
//    public ResponseEntity<?> addSizeChiTiet(@RequestBody AdminSizeChiTietRequest request) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
//        return ResponseEntity.ok(adminSizeChiTietService.add(request));
//    }
//
//    @GetMapping("/checkSize")
//    public ResponseEntity<?> check(@RequestParam Integer idSize, @RequestParam Integer idSP) {
//        return ResponseEntity.ok(adminSizeChiTietService.check(idSP, idSize));
//    }
//
//    @PutMapping("/update-size-chi-tiet/{id}")
//    public ResponseEntity<?> updateSizeChiTiet(@PathVariable Integer id, @RequestBody AdminSizeChiTietRequest request) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
//        return ResponseEntity.ok(adminSizeChiTietService.update(id, request));
//    }
//
//    @DeleteMapping("/delete-size-chi-tiet/{id}")
//    public ResponseEntity<?> deleteSizeChiTiet(@PathVariable Integer id) {
//        adminSizeChiTietService.delete(id);
//        return ResponseEntity.ok("");
//    }

}
