package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.service.EmailSenderService;
import com.example.demo.core.khachHang.service.ResetPasswordService;
import com.example.demo.entity.ResetPasswordToken;
import com.example.demo.entity.User;
import com.example.demo.reponsitory.ResetPasswordTokenRepository;
import com.example.demo.reponsitory.UserReponsitory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final String BASE_FRONTEND_ENDPOINT;
    private final UserReponsitory userRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final SpringTemplateEngine springTemplateEngine;

    public ResetPasswordServiceImpl(@Qualifier("userReponsitory") UserReponsitory userRepository,
                                    ResetPasswordTokenRepository resetPasswordTokenRepository,
                                    EmailSenderService emailSenderService,
                                    SpringTemplateEngine springTemplateEngine,
                                    PasswordEncoder passwordEncoder,
                                    @Value("${frontend.base-endpoint}") String BASE_FRONTEND_ENDPOINT) {
        this.userRepository = userRepository;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
        this.springTemplateEngine = springTemplateEngine;
        this.emailSenderService = emailSenderService;
        this.passwordEncoder = passwordEncoder;
        this.BASE_FRONTEND_ENDPOINT = BASE_FRONTEND_ENDPOINT;
    }

    @Override
    @Transactional
    public boolean handleForgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return false;
        resetPasswordTokenRepository.findFirstByOrderByThoiGianTaoDesc().ifPresent(oldToken -> {
            oldToken.setHieuLuc(false);
            resetPasswordTokenRepository.save(oldToken);
        });
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setUser(user);
        resetPasswordToken.setToken(System.currentTimeMillis() + "-" + UUID.randomUUID());
        resetPasswordToken.setThoiGianTao(LocalDateTime.now());
        resetPasswordToken.setThoiGianHetHan(LocalDateTime.now().plusMinutes(15));
        resetPasswordToken.setHieuLuc(true);
        this.sendResetPasswordMail(user.getEmail(), user.getTen(), resetPasswordToken.getToken());
        resetPasswordTokenRepository.save(resetPasswordToken);
        return true;
    }

    @Override
    public void sendResetPasswordMail(String email, String ten, String token) {
        Map<String, Object> templateProps = new HashMap<>();
        templateProps.put("ten", ten);
        templateProps.put("url", BASE_FRONTEND_ENDPOINT + "/reset-password?email=" + email + "&token=" + token);
        Context context = new Context();
        context.setVariables(templateProps);
        String mailContent = springTemplateEngine.process("reset-password", context);
        emailSenderService.sendSimpleEmail(email, "[VNK] Yêu cầu đặt lại mật khẩu", mailContent);
    }

    @Override
    @Transactional
    public boolean resetPassword(String token, String email, @Nullable String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        ResetPasswordToken resetPasswordToken = user != null ? resetPasswordTokenRepository.findByTokenAndUserId(token, user.getId()).orElse(null) : null;
        if (resetPasswordToken == null) return false;
        if (!resetPasswordToken.getHieuLuc()) return false;
        if (resetPasswordToken.getThoiGianHetHan().isBefore(LocalDateTime.now())) {
            resetPasswordToken.setHieuLuc(false);
            resetPasswordTokenRepository.save(resetPasswordToken);
            return false;
        }
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
            resetPasswordToken.setHieuLuc(false);
            userRepository.save(user);
            resetPasswordTokenRepository.save(resetPasswordToken);
            return true;
        }
        return true;
    }
}
