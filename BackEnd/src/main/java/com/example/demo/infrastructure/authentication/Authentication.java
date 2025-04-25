package com.example.demo.infrastructure.authentication;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class Authentication implements AuthenticationProvider {

    @Autowired
    private AdUserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public org.springframework.security.core.Authentication authenticate(org.springframework.security.core.Authentication authentication) throws AuthenticationException {
        String usernameOrEmail = authentication.getName();
        String password =  authentication.getCredentials().toString();


        try {
            User user = userRepo.findByUserName(usernameOrEmail);

            if(user == null){

                user = userRepo.findByEmail(usernameOrEmail)
                        .orElseThrow(() ->
                        new  UsernameNotFoundException("Đăng nhập không thành công  " ) {
                        });

            }

            boolean matches = passwordEncoder.matches(password,user.getPassword());

            if(matches == false){
                throw new BadCredentialsException("Đăng nhập không thành công");
            }

        } catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("Đăng nhập không thành công  " );
        }

        return new UsernamePasswordAuthenticationToken(usernameOrEmail, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
