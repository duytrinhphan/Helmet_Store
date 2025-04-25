package com.example.demo.infrastructure.mapper;

import com.example.demo.core.Admin.model.response.BHTQChiTietSanPhamResponse;
import com.example.demo.entity.SanPhamChiTiet;
import org.mapstruct.Mapper;

@Mapper
public interface BHTQChiTietSanPhamRespMapper extends AbstractMapper<BHTQChiTietSanPhamResponse, SanPhamChiTiet> {
}
