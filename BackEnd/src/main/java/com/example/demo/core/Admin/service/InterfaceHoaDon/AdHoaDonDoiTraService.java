package com.example.demo.core.Admin.service.InterfaceHoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonChitietResponse;
import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;

public interface AdHoaDonDoiTraService {

    AdminHoaDonChitietResponse huyHoaDonTrahang(Integer idHD, String lyDo, Integer idSPCT);

    AdminHoaDonChitietResponse xacNhanHoaDonTraHang(Integer idHD,Integer idSPCT);

    AdminHoaDonResponse congSoLuongSP(Integer idHD);

    AdminHoaDonResponse khongCongSoLuongSP(Integer idHD);

}
