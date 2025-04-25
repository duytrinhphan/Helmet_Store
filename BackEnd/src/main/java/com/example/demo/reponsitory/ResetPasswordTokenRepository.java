package com.example.demo.reponsitory;

import com.example.demo.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Integer> {
    Optional<ResetPasswordToken> findFirstByOrderByThoiGianTaoDesc();
    Optional<ResetPasswordToken> findByTokenAndUserId(String token, Integer userId);
}