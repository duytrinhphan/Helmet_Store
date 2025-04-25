package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.KHDiaChiRequest;
import com.example.demo.core.khachHang.model.response.DiaChiResponse;
import com.example.demo.core.khachHang.repository.KHDiaChiRepository;
import com.example.demo.core.khachHang.repository.KHUserRepository;
import com.example.demo.core.khachHang.service.DiaChiService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.User;
import com.example.demo.util.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class DiaChiServiceImpl implements DiaChiService {

    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;

    @Autowired
    private KHUserRepository repository;

    @Autowired
    private KHDiaChiRepository khDiaChiRepo;


    @Override
    public List<DiaChi> getAll(String token) {
        Integer idKh;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = repository.findAllByUserName(userName);
        idKh = user.getId();
        return khDiaChiRepo.findDiaChiByIdUsers(idKh);
    }

//    @Override
//    public List<DiaChi> getUserByDiaChi(Integer idUser) {
//        return DCrepository.findDiaChiByIdUser(idUser);
//    }

    @Override
    public DiaChi addDiaChi(KHDiaChiRequest request,String token) {
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);

        List<DiaChiResponse> lstDiaChi = khDiaChiRepo.findDiaChiByIdUser(user.getId());
        if(lstDiaChi.isEmpty()){
            DiaChi diaChi = new DiaChi();
            diaChi.setIdTinhThanh(request.getIdTinhThanh());
            diaChi.setTenTinhThanh(request.getTinhThanh());
            diaChi.setIdQuanHuyen(request.getIdQuanHuyen());
            diaChi.setTenQuanHuyen(request.getQuanHuyen());
            diaChi.setIdphuongXa(request.getIdPhuongXa());
            diaChi.setTenphuongXa(request.getPhuongXa());
            diaChi.setDiaChi(request.getDiaChi());
            diaChi.setTrangThai(1);
            diaChi.setUser(user);
            DiaChi saveDiaChi =  khDiaChiRepo.save(diaChi);
            return khDiaChiRepo.findById(saveDiaChi.getId()).get();
        }
          DiaChi diaChi = new DiaChi();
        diaChi.setIdTinhThanh(request.getIdTinhThanh());
        diaChi.setTenTinhThanh(request.getTinhThanh());
        diaChi.setIdQuanHuyen(request.getIdQuanHuyen());
        diaChi.setTenQuanHuyen(request.getQuanHuyen());
        diaChi.setIdphuongXa(request.getIdPhuongXa());
        diaChi.setTenphuongXa(request.getPhuongXa());
        diaChi.setDiaChi(request.getDiaChi());
        diaChi.setTrangThai(0);
        diaChi.setUser(user);
       DiaChi saveDiaChi =  khDiaChiRepo.save(diaChi);
        return khDiaChiRepo.findById(saveDiaChi.getId()).get();
    }

    @Override
    public DiaChi updateDiaChi(KHDiaChiRequest request, Integer id ,String token) {
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);

        Optional<DiaChi> optional = khDiaChiRepo.findById(id);
        if (optional.isPresent()) {
            DiaChi diaChi = optional.get();
            diaChi.setIdTinhThanh(request.getIdTinhThanh());
            diaChi.setTenTinhThanh(request.getTinhThanh());
            diaChi.setIdQuanHuyen(request.getIdQuanHuyen());
            diaChi.setTenQuanHuyen(request.getQuanHuyen());
            diaChi.setIdphuongXa(request.getIdPhuongXa());
            diaChi.setTenphuongXa(request.getPhuongXa());
            diaChi.setDiaChi(request.getDiaChi());
            diaChi.setUser(user);
            DiaChi saveDiaChi =  khDiaChiRepo.save(diaChi);
            return khDiaChiRepo.findById(saveDiaChi.getId()).get();
        }
        return null;
    }

    @Override
    public Optional<DiaChi> delete(Integer id) {
        Optional<DiaChi> optional = khDiaChiRepo.findById(id);
        if (optional.isPresent()) {
            khDiaChiRepo.deleteById(id);
        }
        return optional;
    }

    public DiaChi thietLapMacDinh(Integer id, String token) {
        DiaChi optional = khDiaChiRepo.findById(id).get();
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = repository.findAllByUserName(userName);

        List<DiaChi> lstDiaChi = khDiaChiRepo.findDiaChiBy(user.getId());
        for (DiaChi o : lstDiaChi) {
            if (o.getId() == id) {
                o.setTrangThai(1);
                khDiaChiRepo.save(o);
            } else {
                o.setTrangThai(0);
                khDiaChiRepo.save(o);
            }
        }
        return optional;
    }

    public DiaChi findDiaChiByIdUserAndTrangThai(String token) {

        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = repository.findAllByUserName(userName);
        return khDiaChiRepo.findDiaChiByIdUserAndTrangThai(user.getId());
    }
}
