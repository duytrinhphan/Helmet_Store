package com.example.demo.infrastructure.mapper;

import com.example.demo.core.Admin.model.response.BHTQPhuongThucThanhToanResponse;
import com.example.demo.entity.PhuongThucThanhToan;
import org.mapstruct.Mapper;

@Mapper
public interface BHTQPhuongThucThanhToanRespMapper extends AbstractMapper<BHTQPhuongThucThanhToanResponse, PhuongThucThanhToan> {
}
