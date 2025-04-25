package com.example.demo.core.khachHang.repository;


import com.example.demo.core.khachHang.model.response.*;
import com.example.demo.reponsitory.SanPhamReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KHSanPhamRepository extends SanPhamReponsitory {

    @Query(value = """
                     SELECT sp.id as id,sp.anh as anh, sp.ma as ma, sp.ten as ten,
                            sp.mo_ta as moTa, sp.trang_thai as trangThai, sp.ngay_tao as ngayTao,
                            sp.ngay_sua as ngaySua, sp.dem_lot as demLot, sp.quai_deo as quaiDeo,
                            th.ten as thuongHieu, vl.ten as vatLieu, l.ten as loai
                             FROM datn.san_pham sp join datn.thuong_hieu th on sp.id_thuong_hieu = th.id
                                                   join datn.vat_lieu vl on sp.id_vat_lieu = vl.id
                                                   join datn.loai l on sp.id_loai = l.id 
                     WHERE sp.id=:id
            """, nativeQuery = true)
    KHSanPhamResponse findByIdSP(Integer id);

    @Query(value = """ 
                           SELECT
                                  spct.id,sp.ma,sp.ten,spct.gia_ban as giaBan,spct.gia_nhap as giaNhap,
                                  spct.so_luong_ton as soLuongTon,spct.trang_thai as trangThai,
                                  sp.quai_deo as quaiDeo,sp.dem_lot as demLot,
                                  sp.mo_ta as moTa,l.ten AS loai,spct.anh as anh,
                                  th.ten as thuongHieu, km.ten as tenKM,s.ten as tenSize, ms.ten as tenMauSac,
                                  km.thoi_gian_bat_dau as thoiGianBatDau, km.thoi_gian_ket_thuc as thoiGianKetThuc,
                                  spct.gia_sau_giam as giaSauGiam, km.gia_tri_giam as giaTriGiam,
                                  tl.value as trongLuong, tl.don_vi as donVi
                           FROM datn.san_pham_chi_tiet spct join datn.san_pham sp on spct.id_san_pham = sp.id
                                    join datn.mau_sac ms on spct.id_mau_sac = ms.id
                                   left join datn.size s on spct.id_size = s.id
                                   join datn.loai l  on sp.id_loai = l.id
                                   join datn.thuong_hieu th on sp.id_thuong_hieu = th.id
                                    join datn.trong_luong tl on spct.id_trong_luong = tl.id
                                    left join datn.khuyen_mai km on spct.id_khuyen_mai = km.id
                           WHERE sp.id  =:id
            """, nativeQuery = true)
    List<KHSanPhamChiTiet2Response> get(@Param("id") Integer id);


    @Query(value = """
                     select distinct  spct.id_size as idSize, s.ten as ten\s
                                    from datn.san_pham_chi_tiet spct join datn.size s on spct.id_size = s.id\s
                                    where spct.id_san_pham =:id
            """, nativeQuery = true)
    List<KhSizeResponse> findSize(@Param("id") Integer id);

    @Query(value = """
                   SELECT i.anh as anh FROM datn.image i where i.id_san_pham =:id
            """, nativeQuery = true)
    List<KhImageResponse> findImage(@Param("id") Integer id);

    @Query(value = """
                    WITH numbered_rows AS (
                    SELECT spct.id_mau_sac as idMauSac, s.ten as ten, spct.anh as anh,
                           ROW_NUMBER() OVER(PARTITION BY spct.id_mau_sac, s.ten ORDER BY spct.anh) as row_num
                    FROM datn.san_pham_chi_tiet spct\s
                    JOIN datn.mau_sac s ON spct.id_mau_sac = s.id
                    WHERE spct.id_san_pham =:id AND spct.trang_thai = 1)
                    SELECT idMauSac, ten, anh
                    FROM numbered_rows
                    WHERE row_num = 1
            """, nativeQuery = true)
    List<KhMauSacResponse> findMauSac(@Param("id") Integer id);


    @Query(value = """
                    select distinct  spct.id_size as idSize, s.ten as ten\s
                    from datn.san_pham_chi_tiet spct join datn.size s on spct.id_size = s.id\s
                    where spct.id_mau_sac =:idMau and spct.id_san_pham=:idSP and spct.trang_thai=1
            """, nativeQuery = true)
    List<KhSizeResponse> findSizeByMauSac(Integer idMau, Integer idSP);

    @Query(value = """
                   select distinct  spct.id_mau_sac as idMauSac,s.ten as ten, spct.anh as anh \s
                   from datn.san_pham_chi_tiet spct join datn.mau_sac s on spct.id_mau_sac = s.id\s
                   where spct.id_size=:idSize and spct.id_san_pham =:idSP
            """, nativeQuery = true)
    List<KhMauSacResponse> findMauSacBySize(Integer idSize, Integer idSP);

    @Query(value = """ 
                           SELECT
                                  spct.id,sp.ma,sp.ten,spct.gia_ban as giaBan,spct.gia_nhap as giaNhap,
                                  spct.so_luong_ton as soLuongTon,spct.trang_thai as trangThai,
                                  sp.quai_deo as quaiDeo,sp.dem_lot as demLot,
                                  sp.mo_ta as moTa,l.ten AS loai,spct.anh as anh,
                                  th.ten as thuongHieu, km.ten as tenKM,s.ten as tenSize, ms.ten as tenMauSac,
                                  km.thoi_gian_bat_dau as thoiGianBatDau, km.thoi_gian_ket_thuc as thoiGianKetThuc,
                                  spct.gia_sau_giam as giaSauGiam, km.gia_tri_giam as giaTriGiam,
                                  tl.value as trongLuong, tl.don_vi as donVi
                           FROM datn.san_pham_chi_tiet spct join datn.san_pham sp on spct.id_san_pham = sp.id
                                    join datn.mau_sac ms on spct.id_mau_sac = ms.id
                                   left join datn.size s on spct.id_size = s.id
                                   join datn.loai l  on sp.id_loai = l.id
                                   join datn.thuong_hieu th on sp.id_thuong_hieu = th.id
                                    join datn.trong_luong tl on spct.id_trong_luong = tl.id
                                    left join datn.khuyen_mai km on spct.id_khuyen_mai = km.id
                           WHERE ms.id=:idMau and s.id =:idSize and sp.id=:idSP
            """, nativeQuery = true)
    KHSanPhamChiTiet2Response getSanPhamChiTietBySizeAndMauSac(Integer idMau, Integer idSize, Integer idSP);

    @Query(value = """ 
                           SELECT
                                  spct.id,sp.ma,sp.ten,spct.gia_ban as giaBan,spct.gia_nhap as giaNhap,
                                  spct.so_luong_ton as soLuongTon,spct.trang_thai as trangThai,
                                  sp.quai_deo as quaiDeo,sp.dem_lot as demLot,
                                  sp.mo_ta as moTa,l.ten AS loai,spct.anh as anh,
                                  th.ten as thuongHieu, km.ten as tenKM,s.ten as tenSize, ms.ten as tenMauSac,
                                  km.thoi_gian_bat_dau as thoiGianBatDau, km.thoi_gian_ket_thuc as thoiGianKetThuc,
                                  spct.gia_sau_giam as giaSauGiam, km.gia_tri_giam as giaTriGiam,
                                  tl.value as trongLuong, tl.don_vi as donVi
                           FROM datn.san_pham_chi_tiet spct join datn.san_pham sp on spct.id_san_pham = sp.id
                                    join datn.mau_sac ms on spct.id_mau_sac = ms.id
                                   left join datn.size s on spct.id_size = s.id
                                   join datn.loai l  on sp.id_loai = l.id
                                   join datn.thuong_hieu th on sp.id_thuong_hieu = th.id
                                    join datn.trong_luong tl on spct.id_trong_luong = tl.id
                                    left join datn.khuyen_mai km on spct.id_khuyen_mai = km.id
                           WHERE ms.id=:idMau  and sp.id=:idSP
            """, nativeQuery = true)
    KHSanPhamChiTiet2Response getSanPhamChiTietAndMauSac(Integer idMau, Integer idSP);

    @Query(value = """
        SELECT sp.id as id, sp.anh as anh, sp.dem_lot as demLot, sp.ma as ma, sp.mo_ta as moTa, sp.quai_deo as quaiDeo, sp.ten as ten
        , sp.trang_thai as trangThai, l.ten as tenLoai, t.ten as tenThuongHieu,
        (select max(spct.gia_ban) as giaBan from datn.san_pham_chi_tiet spct where id_san_pham = sp.id) as giaBanMax,
        (select min(spct.gia_ban) as giaBan from datn.san_pham_chi_tiet spct where id_san_pham = sp.id) as giaBanMin,
        (select max(spct.gia_sau_giam) as giaSauGiam from datn.san_pham_chi_tiet spct where id_san_pham = sp.id and id_khuyen_mai is not null) as giaSauGiamMax,
        (select min(spct.gia_sau_giam) as giaSauGiam from datn.san_pham_chi_tiet spct where id_san_pham = sp.id ) as giaSauGiamMin,
        sp.ngay_tao as ngayTao
        FROM datn.san_pham sp
        join datn.loai l on l.id = sp.id_loai
        join datn.thuong_hieu t on t.id = sp.id_thuong_hieu
         where sp.trang_thai = 1
        ORDER BY sp.id DESC;
        """, nativeQuery = true)
    List<SanPhamResponse> getAllSP();

    @Query(value = """
        SELECT sp.id as id, sp.anh as anh, sp.dem_lot as demLot, sp.ma as ma, sp.mo_ta as moTa, sp.quai_deo as quaiDeo, sp.ten as ten
        , sp.trang_thai as trangThai, l.ten as tenLoai, t.ten as tenThuongHieu,
        (select max(spct.gia_ban) as giaBan from datn.san_pham_chi_tiet spct where id_san_pham = sp.id) as giaBanMax,
        (select min(spct.gia_ban) as giaBan from datn.san_pham_chi_tiet spct where id_san_pham = sp.id) as giaBanMin,
        (select max(spct.gia_sau_giam) as giaSauGiam from datn.san_pham_chi_tiet spct where id_san_pham = sp.id and id_khuyen_mai is not null) as giaSauGiamMax,
        (select min(spct.gia_sau_giam) as giaSauGiam from datn.san_pham_chi_tiet spct where id_san_pham = sp.id ) as giaSauGiamMin,
        sp.ngay_tao as ngayTao
        FROM datn.san_pham sp
        join datn.loai l on l.id = sp.id_loai
        join datn.thuong_hieu t on t.id = sp.id_thuong_hieu
         where sp.trang_thai = 1 and sp.id = :id
        ORDER BY sp.id DESC;
        """, nativeQuery = true)
    SanPhamResponse findSPByIdSP(@Param("id") Integer id);
}
