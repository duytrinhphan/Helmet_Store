package com.example.demo.core.khachHang.repository;


import com.example.demo.core.khachHang.model.response.GioHangCTResponse;
import com.example.demo.core.khachHang.model.response.KhVoucherResponse;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.reponsitory.GioHangChiTietReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KHGioHangChiTietRepository extends GioHangChiTietReponsitory {

    @Query(value = """
          select ghct.id as idGHCT, spct.id as idCTSP ,spct.trang_thai as trangThaiSPCT, sp.trang_thai as trangThaiSP,spct.so_luong_ton as soLuongTon,sp.id as idSP, sp.ten as tenSP, sp.anh,spct.anh as anhSpct, ms.ten as tenMauSac, s.ten as tenSize, ghct.so_luong as soLuong, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam
                                                                  from gio_hang_chi_tiet ghct join san_pham_chi_tiet spct on ghct.id_san_pham_chi_tiet = spct.id
                                                                 left join datn.size s on spct.id_size = s.id\s
                                                                  join datn.mau_sac ms on spct.id_mau_sac = ms.id\s
                                                                  join datn.gio_hang gh on  ghct.id_gio_hang = gh.id
                                                                  join san_pham sp on spct.id_san_pham = sp.id
                                                                  where gh.id_user =:idUser
             """, nativeQuery = true)
    List<GioHangCTResponse> getListGHCT(Integer idUser);

    @Query(value = """
          select ghct.id as idGHCT, spct.id as idCTSP ,spct.trang_thai as trangThaiSPCT, sp.trang_thai as trangThaiSP,spct.so_luong_ton as soLuongTon,sp.id as idSP, sp.ten as tenSP, sp.anh,spct.anh as anhSpct, ms.ten as tenMauSac, s.ten as tenSize, ghct.so_luong as soLuong, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam
                                                                  from gio_hang_chi_tiet ghct join san_pham_chi_tiet spct on ghct.id_san_pham_chi_tiet = spct.id
                                                                left     join datn.size s on spct.id_size = s.id\s
                                                                  join datn.mau_sac ms on spct.id_mau_sac = ms.id\s
                                                                  join datn.gio_hang gh on  ghct.id_gio_hang = gh.id
                                                                  join san_pham sp on spct.id_san_pham = sp.id
                                                                  where gh.id_user =:idUser and ghct.id=:idGH
             """, nativeQuery = true)
    GioHangCTResponse getGHCT(Integer idUser,Integer idGH);

    @Query("Select pt from GioHangChiTiet pt where pt.gioHang.user.id=:id and pt.sanPhamChiTiet.id=:idctsp")
    List<GioHangChiTiet> findById(Integer id, Integer idctsp);

    @Query("Select pt from GioHangChiTiet pt where pt.gioHang.user.id=:id and pt.sanPhamChiTiet.id=:idctsp")
    GioHangChiTiet findByIdByIdCTSP(Integer id, Integer idctsp);

    @Query(value = """
        select count(*) from gio_hang_chi_tiet ghct join gio_hang gh on  ghct.id_gio_hang = gh.id\s
        join user u on gh.id_user = u.id  where u.id = :userId
        """, nativeQuery = true)
    Integer countGHCTByUser(Integer userId);


    @Query(value = """
       SELECT uv.id as id, v.gia_tri_giam as dieuKien, v.giam_toi_da as giamToiDa, v.mo_ta as moTa, v.so_luong as soLuong, v.ten as ten,v.thoi_gian_bat_dau as thoiGianBatDau
              ,v.thoi_gian_ket_thuc as thoiGianKetThuc ,v.trang_thai as trangThai
       FROM datn.user_voucher uv join datn.user u on uv.id_user = u.id
                                 join datn.voucher v on uv.id_voucher = v.id
       where   uv.id_user=:idUser  and v.so_luong >= 0 and v.trang_thai = 0 
        """, nativeQuery = true)
    List<KhVoucherResponse> getListVoucher(Integer  idUser);


    @Query(value = """
       SELECT v.id as id, v.gia_tri_giam as giaTriGiam, v.giam_toi_da as giamToiDa, v.mo_ta as moTa, v.so_luong as soLuong, v.ten as ten,v.thoi_gian_bat_dau as thoiGianBatDau
                    ,v.thoi_gian_ket_thuc as thoiGianKetThuc ,v.trang_thai as trangThai
             FROM  datn.voucher v where v.so_luong > 0 and v.trang_thai = 0\s
        """, nativeQuery = true)
    List<KhVoucherResponse> getListVoucherByTrangThai();


    @Query("Select pt from GioHangChiTiet pt where pt.gioHang.user.id=:id and pt.sanPhamChiTiet.id=:idctsp")
    GioHangChiTiet listGHCTByID(Integer id, Integer idctsp);


    @Query(value = """
       SELECT uv.id as id, v.gia_tri_giam as dieuKien, v.giam_toi_da as giamToiDa, v.mo_ta as moTa, v.so_luong as soLuong, v.ten as ten,v.thoi_gian_bat_dau as thoiGianBatDau
              ,v.thoi_gian_ket_thuc as thoiGianKetThuc ,v.trang_thai as trangThai
       FROM datn.user_voucher uv join datn.user u on uv.id_user = u.id
                                 join datn.voucher v on uv.id_voucher = v.id
       where   uv.id_user=:idUser  and v.so_luong >= 0 and uv.trang_thai = 0 
        """, nativeQuery = true)
    List<KhVoucherResponse> getListVoucherByUser(Integer  idUser);


    @Query(value = """
          select ghct.id as idGHCT, spct.id as idCTSP ,spct.trang_thai as trangThaiSPCT, sp.trang_thai as trangThaiSP,spct.so_luong_ton as soLuongTon,sp.id as idSP, sp.ten as tenSP, sp.anh,spct.anh as anhSpct, ms.ten as tenMauSac, s.ten as tenSize, ghct.so_luong as soLuong, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam
                                                                  from gio_hang_chi_tiet ghct join san_pham_chi_tiet spct on ghct.id_san_pham_chi_tiet = spct.id
                                                                 left join datn.size s on spct.id_size = s.id\s
                                                                  join datn.mau_sac ms on spct.id_mau_sac = ms.id\s
                                                                  join datn.gio_hang gh on  ghct.id_gio_hang = gh.id
                                                                  join san_pham sp on spct.id_san_pham = sp.id
                                                                  where gh.id_user =:idUser and spct.id =:idspct
             """, nativeQuery = true)
    GioHangCTResponse getGHCTByIDCTSP(Integer idUser, Integer idspct);

}
