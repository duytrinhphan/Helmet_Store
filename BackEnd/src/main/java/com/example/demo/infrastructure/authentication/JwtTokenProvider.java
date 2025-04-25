package com.example.demo.infrastructure.authentication;

import com.example.demo.entity.User;
import com.example.demo.infrastructure.exception.BlogAPIException;
import com.example.demo.reponsitory.UserReponsitory;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    UserReponsitory userRepository;

    public JwtTokenProvider(@Qualifier("adUserRepository") UserReponsitory userRepository) {
        this.userRepository = userRepository;
    }

    // generate JWT token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("id", user != null ? user.getId() : null)
                .claim("role", user != null ? user.getRole() : null)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from Jwt token
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

    // validate Jwt token
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }


    // refresh  JWT token
    public boolean isTokenExpired(String accessToken){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            Date expirationDate = claims.getExpiration();

            return expirationDate.before(new Date());

        } catch (ExpiredJwtException e) {
            return true;
//            throw new UnauthorizedException("Token has expired. Please log in again.");
        }
    }

    // generate JWT token
    public String generateTokenByUser(String username){
        User user = userRepository.findByEmail(username).orElse(null);
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("id", user != null ? user.getId() : null)
                .claim("role", user != null ? user.getRole() : null)
                .signWith(key())
                .compact();
        return token;
    }
}
