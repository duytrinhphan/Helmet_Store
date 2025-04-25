package com.example.demo.infrastructure.mapper;

import com.example.demo.core.Admin.model.request.BHTQUserRequest;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface BHTQUserReqMapper extends AbstractMapper<BHTQUserRequest, User> {
}
