package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.service.AdChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/chat")
public class ChatApi {
    private final AdChatService chatService;

    public ChatApi(AdChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(chatService.getAllUser());
    }
}
