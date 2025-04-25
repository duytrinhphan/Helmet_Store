package com.example.demo.infrastructure.mapper;

import com.example.demo.core.Admin.model.response.BHTQUserResponse;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface BHTQUserRespMapper extends AbstractMapper<BHTQUserResponse, User> {
}
