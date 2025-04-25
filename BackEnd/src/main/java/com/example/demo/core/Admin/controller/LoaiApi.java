package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminLoaiRequest;
import com.example.demo.entity.Loai;
import com.example.demo.core.Admin.service.impl.LoaiServiceImpl;
import com.example.demo.util.DataUltil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/admin/loai")
@CrossOrigin(origins = {"*"})
public class LoaiApi {

    @Autowired
    private LoaiServiceImpl loaiService;

    // getAll loai
//    @GetMapping()
//    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", value = "pages") Integer pages) {
//        Page<Loai> page = loaiService.getAll(pages);
//        HashMap<String, Object> map = DataUltil.setData("ok", page);
//        return ResponseEntity.ok(map);
//    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<Loai> page = loaiService.findAll();
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/trang-thai")
    public ResponseEntity<?> getAllByTrangThai(@RequestParam("trangThai") Integer trangThai) {
        List<Loai> page = loaiService.getAllByTrangThai(trangThai);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    // tim kiếm theo ten hoặc mã
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(defaultValue = "0", value = "pages") Integer pages, @RequestParam(required = false) String keyword) {
        Page<Loai> page = loaiService.search(keyword, pages);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    // check validate
    @PostMapping("/validation")
    public ResponseEntity<?> validation(@RequestBody @Valid AdminLoaiRequest request, BindingResult result) {
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
    public ResponseEntity<?> add(@RequestBody AdminLoaiRequest request) {
        HashMap<String, Object> map = loaiService.add(request);
        return ResponseEntity.ok(map);
    }

    // sửa
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody AdminLoaiRequest request) {
        HashMap<String, Object> map = loaiService.update(request, id);
        return ResponseEntity.ok(map);
    }

    // xóa (đổi trạng thái về 0)
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        HashMap<String, Object> map = loaiService.delete(id);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) {
        this.loaiService.saveExcel(file);
        HashMap<String, Object> map = DataUltil.setData("success", " thêm sản phẩm thành công");
        return ResponseEntity.ok(map);
    }
}
