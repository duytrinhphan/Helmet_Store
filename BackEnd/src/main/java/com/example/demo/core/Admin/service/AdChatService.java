package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.response.ChatUserResponse;

import java.util.List;

public interface AdChatService {
    List<ChatUserResponse> getAllUser();
}