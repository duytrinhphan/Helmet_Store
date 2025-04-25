package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.response.ChatUserResponse;
import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.Admin.service.AdChatService;
import com.example.demo.entity.Role;
import com.example.demo.infrastructure.mapper.ChatUserRespMapper;
import com.example.demo.infrastructure.status.UserStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdChatServiceImpl implements AdChatService {
    private final AdUserRepository userRepository;
    private final ChatUserRespMapper userMapper;

    public AdChatServiceImpl(AdUserRepository userRepository, ChatUserRespMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<ChatUserResponse> getAllUser() {
        return userRepository.findByRoleAndTrangThaiOrderByNgayTaoDesc(Role.USER, UserStatus.DANG_HOAT_DONG).stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
