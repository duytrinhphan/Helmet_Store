package com.example.demo.infrastructure.authentication;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AdUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws RuntimeException {
        User user = userRepo.findUsersByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new RuntimeException("Đăng nhập không thành công "));
        Role role = user.getRole();
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name())));
    }
}
