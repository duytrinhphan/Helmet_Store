package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.GioHangCTRequest;
import com.example.demo.core.khachHang.model.request.KhGioHangChiTietSessionRequest;
import com.example.demo.core.khachHang.model.response.GioHangCTResponse;
import com.example.demo.core.khachHang.model.response.KhCartBO;
import com.example.demo.core.khachHang.model.response.KhVoucherResponse;
import com.example.demo.core.khachHang.repository.KHGioHangChiTietRepository;
import com.example.demo.core.khachHang.service.impl.KHGioHangServiceImpl;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.User;
import com.example.demo.util.DataUltil;
import com.microsoft.azure.storage.StorageException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/khach-hang/giohang")
public class GiopHangCTController {


    @Autowired
    KHGioHangServiceImpl khGioHangService;

    @Autowired
    KHGioHangChiTietRepository khGHCTRespon;

    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;

    @PostMapping("/addGiohang")
    public ResponseEntity<?> addGiohang(@RequestBody GioHangCTRequest ghctrequest, @RequestParam(value = "token", required = false) String token
          ) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
            HashMap<String, Object> map = khGioHangService.addCart(ghctrequest, token);
            return ResponseEntity.ok(map);

    }

    @PostMapping("/addGiohang-session")
    public ResponseEntity<?> addGiohangSesion(@RequestBody GioHangCTRequest ghctrequest, @RequestParam(value = "token", required = false) String token
            , HttpSession httpSession) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
            return ResponseEntity.ok(khGioHangService.addCartSession(ghctrequest.getSanPhamChiTiet(),ghctrequest.getSoLuong(), httpSession));

    }

    @PostMapping("/addGiohang-when-login")
    public ResponseEntity<?> addGiohangWhenLogin(@RequestBody List<KhGioHangChiTietSessionRequest> lstRequest, @RequestParam(value = "token", required = false) String token)  {
        return ResponseEntity.ok(khGioHangService.addCartWhenLogin(lstRequest,token));
    }

    @GetMapping("/{idghct}")
    public GioHangCTResponse getGHCT(@PathVariable("idghct") Integer idghct, @RequestParam String token) {
        Integer idUser;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idUser = user.getId();
        return khGHCTRespon.getGHCT(idUser, idghct);
    }


    @GetMapping("/getListGioHang")
    public List<?> getList(@RequestParam(value = "token") String token) {
           List<GioHangCTResponse> list = khGioHangService.getListGHCT(token);
           return list ;
    }

    @GetMapping("/getGioHangCTByIdctsp")
    public GioHangCTResponse getGioHangCTByIdctsp(@RequestParam(value = "token") String token, @RequestParam(value = "idctsp") Integer idctsp) {

        return khGioHangService.getGHCTByIdCTSP(token,idctsp);
    }

    @GetMapping("/get-voucher")
    public ResponseEntity<?> getListVoucher(@RequestParam("token") String token) {
        List<KhVoucherResponse> getList = khGioHangService.getListVoucher(token);
        return ResponseEntity.ok(getList);
    }

    @GetMapping("/get-voucher-trang-thai")
    public ResponseEntity<?> getListVoucherByTrangThai() {
        List<KhVoucherResponse> getList = khGioHangService.getListVoucherByTrangThai();
        return ResponseEntity.ok(getList);
    }

    @GetMapping("/get-voucher-user")
    public ResponseEntity<?> getListVoucherByUser(@RequestParam("token") String token) {
        List<KhVoucherResponse> getList = khGioHangService.getListVoucherByUser(token);
        return ResponseEntity.ok(getList);
    }

    @PutMapping("/congSL/{idghct}")
    public ResponseEntity<?> updateCongGHCT(HttpSession httpSession, @PathVariable("idghct") Integer idghct, @RequestParam(required = false) String token) {
            GioHangCTResponse map = khGioHangService.updateCongSoLuong(idghct, token);
            return ResponseEntity.ok(map);
    }

    @PutMapping("/updateSL/{idghct}")
    public ResponseEntity<?> updateSLGHCT(HttpSession httpSession, @PathVariable("idghct") Integer idghct, @RequestParam(required = false) String token, @RequestParam("sl") Integer sl) {
        GioHangCTResponse map = khGioHangService.updateSLGH(idghct, token, sl);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/truSL/{idghct}")
    public ResponseEntity<?> updateTruGHCT(HttpSession httpSession, @PathVariable("idghct") Integer idghct, @RequestParam(required = false) String token) {
            GioHangCTResponse map = khGioHangService.updateTruSoLuong(idghct, token);
            return ResponseEntity.ok(map);

    }

    @PostMapping("/updateMauSacSize/{idghct}")
    public ResponseEntity<?> updateMauSacSize(HttpSession httpSession, @PathVariable("idghct") Integer idghct, @RequestParam("idSPCT") Integer idSPCT, @RequestParam(required = false) String token) {
            HashMap<String, Object> map = khGioHangService.updateMauSacSize(idghct, idSPCT);
            return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{idghct}")
    public ResponseEntity<?> deleteGHCT(@PathVariable(value = "idghct") Integer idghct, @RequestParam(required = false) String token, HttpSession httpSession) {

            return ResponseEntity.ok(khGioHangService.deleteGioHangCT(idghct));
    }


    @GetMapping("/countGHCT")
    public Integer countGHCTByUser(@RequestParam("token") String token) {
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        return khGHCTRespon.countGHCTByUser(user.getId());
    }

}

