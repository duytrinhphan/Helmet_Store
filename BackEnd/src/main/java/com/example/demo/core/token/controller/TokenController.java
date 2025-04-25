package com.example.demo.core.token.controller;

import com.example.demo.core.token.service.TokenService;
import com.example.demo.infrastructure.authentication.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/genToken")
    public ResponseEntity<String> login(@RequestParam("username") String username){
        String data =  tokenService.genToken(username);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        if (jwtTokenProvider.validateToken(token)){
            return ResponseEntity.ok("JWT token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JWT token is not valid");
        }
    }

    @GetMapping("/getUseNameByToken")
    public String getUseNameByToken(@RequestParam("token") String token) {
        return jwtTokenProvider.getUsername(token);
    }

}
