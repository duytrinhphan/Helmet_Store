package com.example.demo.core.Admin.service.AdThongKeService;

import com.example.demo.core.Admin.model.response.AdminThongKeLoiNhuanBo;

public interface AdThongKeLoiNhuanService {

    AdminThongKeLoiNhuanBo getAll(String year, String startDate, String endDate);

    AdminThongKeLoiNhuanBo getAllByHinhThucGiaoHang(Integer idInteger);
}
