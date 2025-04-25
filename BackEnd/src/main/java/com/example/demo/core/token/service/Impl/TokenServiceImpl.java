package com.example.demo.core.token.service.Impl;

import com.example.demo.core.token.service.TokenService;
import com.example.demo.infrastructure.authentication.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public String genToken(String username) {
        String test = jwtTokenProvider.generateTokenByUser(username);
        return test;
    }

    @Override
    public String getUserNameByToken(String token){
        String userName = jwtTokenProvider.getUsername(token);
        return userName;
    }


}
