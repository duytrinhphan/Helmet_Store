package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminVoucherRequest;
import com.example.demo.core.Admin.service.AdVoucherService;
import com.example.demo.core.Admin.service.impl.AdExcelVoucherServiceImpl;
import com.example.demo.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/voucher")
public class VoucherApi {

    @Autowired
    private AdVoucherService voucherService;

    @Autowired
    private AdExcelVoucherServiceImpl adExcelVoucherService;

    @GetMapping("/getAllVoucher")
    public List<Voucher> getAllVoucher() {
        return voucherService.getAllVoucher();
    }

    @GetMapping("/get-user-by-voucher/{idVoucher}")
    public ResponseEntity<?> getAllUserByVoucher(@PathVariable Integer idVoucher) {
        return ResponseEntity.ok(voucherService.getUserByVoucher(idVoucher));
    }

    // thêm
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AdminVoucherRequest request) {
        HashMap<String, Object> map = voucherService.add(request);
        return ResponseEntity.ok(map);
    }

    // sửa
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody AdminVoucherRequest request) {
        HashMap<String, Object> map = voucherService.update(request, id);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestBody AdminVoucherRequest request) {
        HashMap<String, Object> map = voucherService.delete(request, id);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/view-data")
    public ResponseEntity<?> viewDataImportExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(adExcelVoucherService.previewDataImportExcel(file));
    }
}
