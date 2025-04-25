package com.example.demo.core.Admin.service;


import com.example.demo.core.Admin.model.request.AdminDiaChiRequest;
import com.example.demo.core.Admin.model.request.AdminUserRequest;
import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.User;

import java.util.List;

public interface AdUserService {

    List<AdminUserResponse> getKhachHang();

    List<AdminUserResponse> getNhanVien();

    List<AdminUserResponse> getAdmin();

    List<User> getAllByTrangThai(Integer trangThai);

    List<DiaChi> getUserByDiaChi(Integer idUser);

    AdminUserResponse add(AdminUserRequest user);

    AdminUserResponse delete(Integer id);

    AdminUserResponse update(AdminUserRequest user, Integer id);

    AdminUserResponse VoHieuHoaUser(Integer id);

    AdminHoaDonResponse getHoaDonByIdUser(Integer id);

    List<AdminUserResponse> getAllUserByRole(String role);

    List<AdminUserResponse> getAllUser();

    DiaChi addDiaChi(AdminDiaChiRequest request);
}
