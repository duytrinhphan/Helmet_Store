package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminMauSacRequest;
import com.example.demo.core.Admin.model.request.AdminVatLieuRequest;
import com.example.demo.core.Admin.service.AdVatLieuServcie;
import com.example.demo.core.Admin.service.impl.VatLieuServiceImpl;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.VatLieu;
import com.example.demo.util.DataUltil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/admin/vat-lieu")
@CrossOrigin(origins = {"*"})
public class VatLieuApi {

    @Autowired
    private VatLieuServiceImpl service;

//    @GetMapping()
//    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", value = "pages") Integer pages) {
//        Page<MauSac> page = mauSacService.getAll(pages);
//        HashMap<String, Object> map = DataUltil.setData("ok", page);
//        return ResponseEntity.ok(map);
//    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<VatLieu> page = service.findAll();
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/trang-thai")
    public ResponseEntity<?> getAllByTrangThai(@RequestParam("trangThai") Integer trangThai) {
        List<VatLieu> page = service.getAllByTrangThai(trangThai);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    // check validate
    @PostMapping("/validation")
    public ResponseEntity<?> validation(@RequestBody @Valid AdminMauSacRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            HashMap<String, Object> map = DataUltil.setData("error", list);
            return ResponseEntity.ok(map);
        } else {
            HashMap<String, Object> map = DataUltil.setData("ok", "");
            return ResponseEntity.ok(map);
        }
    }


    // thêm
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AdminVatLieuRequest request) {
        HashMap<String, Object> map = service.add(request);
        return ResponseEntity.ok(map);
    }

    // sửa
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody AdminVatLieuRequest request) {
        HashMap<String, Object> map = service.update(request, id);
        return ResponseEntity.ok(map);
    }

    // xóa (đổi trạng thái về 0)
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        HashMap<String, Object> map = service.delete(id);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) {
        this.service.saveExcel(file);
        HashMap<String, Object> map = DataUltil.setData("success", " thêm vật liệuz thành công");
        return ResponseEntity.ok(map);
    }
}
