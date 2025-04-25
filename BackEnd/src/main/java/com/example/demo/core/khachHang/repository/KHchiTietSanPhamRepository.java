package com.example.demo.core.khachHang.repository;

import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.reponsitory.ChiTietSanPhamReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KHchiTietSanPhamRepository extends ChiTietSanPhamReponsitory {

    @Query(value = """ 
                          SELECT (SELECT COUNT(*) FROM datn.san_pham_chi_tiet spct
                                                  WHERE spct.trang_thai IN (1, 2, 3)) AS soLuongSanPham,
                                                             ROW_NUMBER() OVER(ORDER BY spct.id DESC) AS stt,                                                              spct.id,sp.ma,sp.ten,spct.gia_ban as giaBan,spct.gia_nhap as giaNhap,
                                                             spct.so_luong_ton as soLuongTon,spct.trang_thai as trangThai,
                                                             sp.quai_deo as quaiDeo,sp.dem_lot as demLot,
                                                             sp.mo_ta as moTa,l.ten AS loai,sp.anh as anh,
                                                             th.ten as thuongHieu, km.id as id, km.ten as tenKM,
                                                             km.thoi_gian_bat_dau as thoiGianBatDau, km.thoi_gian_ket_thuc as thoiGianKetThuc,
                                                             spct.gia_sau_giam as giaSauGiam, km.gia_tri_giam as giaTriGiam,
                                                             vl.ten as vatLieu,tl.value as trongLuong
                            FROM datn.san_pham_chi_tiet spct join datn.san_pham sp on spct.id_san_pham = sp.id
                                                  			 join datn.loai l  on sp.id_loai = l.id
                                                  			 join datn.thuong_hieu th on sp.id_thuong_hieu = th.id
                                                  			 join datn.trong_luong tl on spct.id_trong_luong = tl.id
                                                             join datn.vat_lieu vl on spct.id_vat_lieu = vl.id
                                                             left join datn.khuyen_mai km on spct.id_khuyen_mai = km.id
                         	WHERE spct.id =:id
            """, nativeQuery = true)
    AdminSanPhamChiTietResponse get(@Param("id") Integer id);

    @Query("select spct from SanPhamChiTiet spct where spct.sanPham.id = :idSP")
    List<SanPhamChiTiet> findByIdSp(Integer idSP);

}
