package com.example.demo.core.token.service;

public interface TokenService {
    String genToken(String username);
    String getUserNameByToken(String token);
}
