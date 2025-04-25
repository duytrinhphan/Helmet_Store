package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminThuongHieuRequest;
import com.example.demo.entity.ThuongHieu;
import com.example.demo.core.Admin.service.impl.ThuongHieuServiceImpl;
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
@RequestMapping("/api/admin/thuong-hieu")
@CrossOrigin(origins = {"*"})
public class ThuongHieuApi {

    @Autowired
    private ThuongHieuServiceImpl thuongHieuService;

//    @GetMapping()
//    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", value = "pages") Integer pages) {
//        Page<ThuongHieu> page = thuongHieuService.getAll(pages);
//        HashMap<String, Object> map = DataUltil.setData("ok", page);
//        return ResponseEntity.ok(map);
//    }

    // hiển thị tất cả
    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<ThuongHieu> page = thuongHieuService.findAll();
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    //hiển thị theo trạng thái
    @GetMapping("/trang-thai")
    public ResponseEntity<?> getAllByTrangThai(@RequestParam("trangThai") Integer trangThai) {
        List<ThuongHieu> page = thuongHieuService.getAllByTrangThai(trangThai);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    // tim kiếm theo ten hoặc mã
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(defaultValue = "0", value = "pages") Integer pages, @RequestParam(required = false) String keyword) {
        Page<ThuongHieu> page = thuongHieuService.search(keyword, pages);
        HashMap<String, Object> map = DataUltil.setData("ok", "page");
        return ResponseEntity.ok(map);
    }

    // check validate
    @PostMapping("/validation")
    public ResponseEntity<?> validation(@RequestBody @Valid AdminThuongHieuRequest request, BindingResult result) {
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
    public ResponseEntity<?> add(@RequestBody AdminThuongHieuRequest request) {
        HashMap<String, Object> map = thuongHieuService.add(request);
        return ResponseEntity.ok(map);
    }

    // sửa
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody AdminThuongHieuRequest request) {
        HashMap<String, Object> map = thuongHieuService.update(request, id);
        return ResponseEntity.ok(map);
    }

    // xóa (đổi trạng thái về 0)
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        HashMap<String, Object> map = thuongHieuService.delete(id);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) {
        this.thuongHieuService.saveExcel(file);
        HashMap<String, Object> map = DataUltil.setData("success", " thêm sản phẩm thành công");
        return ResponseEntity.ok(map);
    }
}
