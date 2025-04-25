package com.example.demo.infrastructure.mapper;

import com.example.demo.core.Admin.model.response.BHTQHoaDonChiTietResponse;
import com.example.demo.entity.HoaDonChiTiet;
import org.mapstruct.Mapper;

@Mapper
public interface BHTQHoaDonChiTietRespMapper extends AbstractMapper<BHTQHoaDonChiTietResponse, HoaDonChiTiet> {
}