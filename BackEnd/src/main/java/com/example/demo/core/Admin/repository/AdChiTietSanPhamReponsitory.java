package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.request.AdminSearchRequest;
import com.example.demo.core.Admin.model.response.AdminSPCTResponse;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.reponsitory.ChiTietSanPhamReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdChiTietSanPhamReponsitory extends ChiTietSanPhamReponsitory {


    @Query("select  pt  from  SanPhamChiTiet  pt where pt.trangThai =:trangThai")
    List<SanPhamChiTiet> findAlls(Integer trangThai, Pageable pageable);

    @Query(value = "select  pt  from  SanPhamChiTiet  pt where pt.sanPham.id =:id")
    SanPhamChiTiet findBySanPhamId(Integer id);

    @Query(value = "select  pt  from  SanPhamChiTiet  pt where pt.sanPham.id =:id")
    List<SanPhamChiTiet> findByListSanPhamId(Integer id);

    @Query(value = "select  pt  from  SanPhamChiTiet  pt where pt.sanPham.ten like :ten")
    SanPhamChiTiet findBySanPhamTen(String ten);

    @Query(value = "select  pt  from  SanPhamChiTiet  pt where pt.size.id =:idSize and  pt.mauSac.id =:idMau and pt.sanPham.id =:idSP and pt.soLuongTon> 0")
    SanPhamChiTiet check(Integer idSize, Integer idMau, Integer idSP);

    @Query(value = """ 
                           SELECT (SELECT COUNT(*) FROM datn.san_pham_chi_tiet spct
                                                   WHERE spct.trang_thai IN (1, 2, 3)) AS soLuongSanPham,
                                   ROW_NUMBER() OVER(ORDER BY spct.id DESC) AS stt,
                                   spct.id, sp.ma, sp.ten, spct.gia_ban AS giaBan, spct.gia_nhap AS giaNhap,
                                   spct.so_luong_ton AS soLuongTon, spct.trang_thai AS trangThai,
                                   sp.quai_deo AS quaiDeo, sp.dem_lot AS demLot,
                                   sp.mo_ta AS moTa, l.ten AS loai, sp.anh AS anh,
                                   th.ten AS thuongHieu, km.ten AS tenKM,
                                   km.thoi_gian_bat_dau AS thoiGianBatDau, km.thoi_gian_ket_thuc AS thoiGianKetThuc,
                                   spct.gia_sau_giam AS giaSauGiam, km.gia_tri_giam AS giaTriGiam,
                                   vl.ten AS vatLieu, tl.value AS trongLuong
                           FROM datn.san_pham_chi_tiet spct
                                   JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                   JOIN datn.loai l ON sp.id_loai = l.id
                                   JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                   JOIN datn.trong_luong tl ON spct.id_trong_luong = tl.id
                                   JOIN datn.vat_lieu vl ON spct.id_vat_lieu = vl.id
                                   LEFT JOIN datn.khuyen_mai km ON spct.id_khuyen_mai = km.id
                           WHERE spct.trang_thai IN (1, 2, 3);
                                                               
            """, nativeQuery = true)
    List<AdminSanPhamChiTietResponse> getAll(@Param("request") AdminSearchRequest request);

    @Query(value = """ 
                          SELECT (SELECT COUNT(*) FROM datn.san_pham_chi_tiet spct
                                                  WHERE spct.trang_thai IN (1, 2, 3)) AS soLuongSanPham,
                                                             ROW_NUMBER() OVER(ORDER BY spct.id DESC) AS stt,                                                              spct.id,sp.ma,sp.ten,spct.gia_ban as giaBan,spct.gia_nhap as giaNhap,
                                                             spct.so_luong_ton as soLuongTon,spct.trang_thai as trangThai,
                                                             sp.quai_deo as quaiDeo,sp.dem_lot as demLot,
                                                             sp.mo_ta as moTa,l.ten AS loai,sp.anh as anh,
                                                             th.ten as thuongHieu, km.ten as tenKM,
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


    @Query(value = """ 
                            SELECT     ROW_NUMBER() OVER(ORDER BY spct.id DESC) AS stt,                                                              spct.id,sp.ma,sp.ten,spct.gia_ban as giaBan,spct.gia_nhap as giaNhap,
                                                             spct.so_luong_ton as soLuongTon,spct.trang_thai as trangThai,
                                                             sp.quai_deo as quaiDeo,sp.dem_lot as demLot,
                                                             sp.mo_ta as moTa,l.ten AS loai,sp.anh as anh,
                                                             th.ten as thuongHieu, km.ten as tenKM,
                                                             km.thoi_gian_bat_dau as thoiGianBatDau, km.thoi_gian_ket_thuc as thoiGianKetThuc,
                                                             spct.gia_sau_giam as giaSauGiam, km.gia_tri_giam as giaTriGiam,
                                                             vl.ten as vatLieu,tl.value as trongLuong,
                                                             ( SELECT COUNT(spct.id) FROM datn.san_pham_chi_tiet spct
                                                                                   WHERE (:comboBoxValue = 'conHang' AND spct.trang_thai = 1)
                                                                                       OR (:comboBoxValue = 'hetHang' AND spct.trang_thai = 2)
                                                                                       OR (:comboBoxValue = 'tonKho' AND spct.trang_thai = 3)
                                                                                       OR (:comboBoxValue = 'dangKhuyenMai' AND spct.id_khuyen_mai IS NOT NULL)) AS soLuongSanPham
                            FROM datn.san_pham_chi_tiet spct join datn.san_pham sp on spct.id_san_pham = sp.id
                                                  			 join datn.loai l  on sp.id_loai = l.id
                                                  			 join datn.thuong_hieu th on sp.id_thuong_hieu = th.id
                                                  			 join datn.trong_luong tl on spct.id_trong_luong = tl.id
                                                             join datn.vat_lieu vl on spct.id_vat_lieu = vl.id
                                                             left join datn.khuyen_mai km on spct.id_khuyen_mai = km.id
                            WHERE (CASE
                                   WHEN :comboBoxValue = 'conHang' THEN spct.trang_thai = 1
                                   WHEN :comboBoxValue = 'hetHang' THEN spct.trang_thai = 2
                                   WHEN :comboBoxValue = 'tonKho' THEN spct.trang_thai = 3
                                   WHEN :comboBoxValue = 'dangKhuyenMai' THEN spct.id_khuyen_mai IS NOT NULL
                                   END)
                            GROUP BY  spct.id , spct.so_luong_ton ,spct.trang_thai ,
                                                             sp.quai_deo ,sp.dem_lot ,
                                                             sp.mo_ta ,l.ten ,sp.anh ,
                                                             th.ten , km.ten ,
                                                             km.thoi_gian_bat_dau , km.thoi_gian_ket_thuc ,
                                                             spct.gia_sau_giam , km.gia_tri_giam ,
                                                             vl.ten ,tl.value ;
            """, nativeQuery = true)
    List<AdminSanPhamChiTietResponse> loc(@Param("comboBoxValue") String comboBoxValue);



    @Query(value = """
            select a.id, b.ten as tenSP, a.gia_ban as giaBan, ms.ten as tenMauSac, s.ten as tenSize, a.trang_thai as trangThai from san_pham_chi_tiet a join san_pham b on a.id_san_pham = b.id\s
            join mau_sac ms on ms.id = a.id_mau_sac
          left  join size s on s.id = a.id_size
            LEFT JOIN khuyen_mai km on km.id = a.id_khuyen_mai
             where b.id =:idsp and (a.id_khuyen_mai IS NULL) OR (a.id_khuyen_mai IS NOT NULL AND km.trang_thai not in (0,2))
            """, nativeQuery = true)
    List<AdminSPCTResponse> getListCTSPBySanPham(@Param("idsp") Integer idsp);
}
