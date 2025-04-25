package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.LoginPayLoad;
import com.example.demo.core.khachHang.repository.KHUserRepository;
import com.example.demo.core.khachHang.service.KHUserService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

import java.util.Optional;


@Service
public class KHUserServiceImpl implements KHUserService {

    @Autowired
    private KHUserRepository khUserRepo;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User dangNhapGoogle(String email, String ten, String anh) {
        User user = khUserRepo.findUserByEmail(email);
            return user;
    }

    public User createAccountGG(String email, String ten, String anh){
        User addUser = User.builder().email(email).userName(email).ten(ten).build();
        addUser.setRole(Role.USER);
        addUser.setNgayTao(LocalDateTime.now());
        addUser.setPassword("$2a$12$Xcp214DEIsQr61KrINMt5egl.2Tqfcjwhu32Y9Y5TCEFzH5yiEOlS");
        addUser.setImage(anh);
        addUser.setTrangThai(2);
        User user1 = khUserRepo.save(addUser);
        user1.setMa("US" + user1.getId());
        khUserRepo.save(user1);
        return user1;
    }

    @Override
    public User updateSDT(Integer id, String sdt) {

        User user = khUserRepo.findAllById(id).orElseThrow();

        user.setSdt(sdt);

        khUserRepo.save(user);
        return user;
    }

    @Override
    @Transactional
    public boolean doiMatKhau(Integer userId, String oldPassword, String newPassword) {
        User user = khUserRepo.findById(userId).orElse(null);
        if (user != null) {
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                khUserRepo.save(user);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public User findByToken(String token) {
        Integer idKh;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = khUserRepo.findAllByUserName(userName);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return khUserRepo.findAllByEmail(email);
    }


    @Override
    public String checkValiDate(LoginPayLoad loginPayload) {
        User u = khUserRepo.findUserByEmail(loginPayload.getUsernameOrEmail());
        if (u == null || u.getPassword() != loginPayload.getPassword()) {
            return "Sai tài khoản hoặc mật khẩu";
        } else {
            return "Ok";
        }

    }

}
