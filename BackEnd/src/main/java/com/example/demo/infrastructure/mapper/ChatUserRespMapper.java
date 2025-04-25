package com.example.demo.infrastructure.mapper;

import com.example.demo.core.Admin.model.response.ChatUserResponse;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface ChatUserRespMapper extends AbstractMapper<ChatUserResponse, User> {
}
