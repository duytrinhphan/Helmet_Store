package com.example.demo.core.Admin.model.request;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.util.DatetimeUtil;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminUserRequest implements DtoToEntity<User> {

    private String ten;

    private Integer trangThai;

    private String userName;

    private String ngayTao;

    private String password;

    private String ngaySinh;

    private String image;

    private String email;

    private String sdt;

    private Integer gioiTinh;

    private String diaChi;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Integer chucVu;

    private  PasswordEncoder passwordEncoder;

    public AdminUserRequest() {
        this.passwordEncoder = new BCryptPasswordEncoder(); // Khởi tạo PasswordEncoder
    }

    @Override
    public User dtoToEntity(User user) {

        user.setTen(this.getTen());
        user.setUserName(this.getUserName());
        user.setTrangThai(this.getTrangThai());
        user.setNgayTao(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(this.getPassword()));
        user.setNgaySinh(this.getNgaySinh());
        user.setImage(this.getImage());
        user.setEmail(this.getEmail());
        user.setSdt(this.getSdt());
        user.setGioiTinh(this.getGioiTinh());
        user.setRole(this.getRole());
        //   user.setChucVu(ChucVu.builder().id(this.getChucVu()).build());
        return user;
    }
}
