package com.example.demo.infrastructure.mapper;

import com.example.demo.core.Admin.model.response.BHTQHoaDonResponse;
import com.example.demo.entity.HoaDon;
import org.mapstruct.Mapper;

@Mapper
public interface BHTQHoaDonRespMapper extends AbstractMapper<BHTQHoaDonResponse, HoaDon> {
}