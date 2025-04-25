package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.model.request.KhDoiTraRequest;
import com.example.demo.core.khachHang.service.KHHoaDonService;
import com.example.demo.core.khachHang.service.impl.KHDoiTraHoaDonServiceImpl;
import com.example.demo.core.khachHang.service.impl.ThongBaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/khach-hang/hoa-don")
public class KHHoaDonApi {

    @Autowired
    private KHHoaDonService hdService;

    @Autowired
    private KHDoiTraHoaDonServiceImpl khDoiTraHoaDonService;

    @Autowired
    private ThongBaoServiceImpl thongBaoService;

    @GetMapping("/find-all")
    public ResponseEntity<?> getAll(@RequestParam("token") String token) {
        return ResponseEntity.ok(hdService.getAll(token));
    }

    @GetMapping("/find-all-by-trang-thai")
    public ResponseEntity<?> getAllByTrangThai(@RequestParam("token") String token,
                                               @RequestParam("trangThai") Integer trangThai,
                                               @RequestParam(value = "trangThai2",required = false) Integer trangThai2 ,
                                               @RequestParam(value = "trangThai3",required = false) Integer trangThai3,
                                               @RequestParam(value = "trangThai4",required = false) Integer trangThai4) {
        return ResponseEntity.ok(hdService.getHoaDonTrangThai(token, trangThai,trangThai2,trangThai3,trangThai4));
    }

    @PutMapping("/huy-hoa-don/{id}")
    public ResponseEntity<?> huyHoaDon(@PathVariable Integer id, @RequestParam("lyDo") String lyDo) {
        thongBaoService.huyDonHang(id);
        return ResponseEntity.ok(hdService.huyHoaDonChoXacNhan(id, lyDo));
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(hdService.findById(id
        ));
    }

    @GetMapping("/find-by-ma/{maHD}")
    public ResponseEntity<?> findByMaHD(@PathVariable String maHD) {
        return ResponseEntity.ok(hdService.findByMaHD(maHD));

    }

    @PostMapping("/doi-tra")
    public ResponseEntity<?> doiTra(@RequestBody KhDoiTraRequest khDoiTraRequest) {
        return ResponseEntity.ok(khDoiTraHoaDonService.doiTra(khDoiTraRequest));
    }

    @DeleteMapping("/huy-doi-tra/{id}")
    public ResponseEntity<?> huyDoiTra(@PathVariable Integer id) {
        return ResponseEntity.ok(khDoiTraHoaDonService.huyTra(id));
    }

}