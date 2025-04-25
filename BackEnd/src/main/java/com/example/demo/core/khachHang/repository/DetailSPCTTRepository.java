package com.example.demo.core.khachHang.repository;

import com.example.demo.core.khachHang.model.response.DetailSanPhamResponse;
import com.example.demo.core.khachHang.model.response.MauSacResponse;
import com.example.demo.core.khachHang.model.response.SelectedSanPhamResponse;
import com.example.demo.core.khachHang.model.response.SizeResponse;
import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetailSPCTTRepository extends JpaRepository<SanPhamChiTiet, Integer> {

    @Query(value = """
           select spct.id,sp.ma as maSP, sp.ten as TenSP, th.ten as ThuongHieu,sp.dem_lot as demLot, sp.quai_deo as quaiDeo,
           spct.gia_ban as GiaBan, spct.gia_sau_giam as giaSauGiam,km.ten as tenKM,CONCAT(vl.ten,', ',vl.mo_ta) as VatLieu, l.ten as Loai,
           CONCAT(tl.value, ' ', tl.don_vi)  as TrongLuong
           from san_pham_chi_tiet spct
           join san_pham sp on spct.id_san_pham = sp.id
           join loai l on sp.id_loai = l.id
           join thuong_hieu th on th.id = sp.id_thuong_hieu
           join trong_luong tl on spct.id_trong_luong = tl.id
           join vat_lieu vl on spct.id_vat_lieu = vl.id
           left join khuyen_mai km on spct.id_khuyen_mai = km.id
            where spct.id =:idctsp
            """, nativeQuery = true)
    DetailSanPhamResponse getDetailCTSP(Integer idctsp);


    @Query(value = """
             SELECT
                   SUM(IFNULL(msct.so_luong, 0) + IFNULL(sct.so_luong, 0)) AS so_luong_ton_tong
            FROM san_pham_chi_tiet spct
            JOIN san_pham sp ON spct.id_san_pham = sp.id
            LEFT JOIN (
                SELECT id_ctsp, SUM(so_luong) AS so_luong
                FROM mau_sac_ctsp
                WHERE id_ctsp =:idctsp
                GROUP BY id_ctsp
            ) msct ON spct.id = msct.id_ctsp
            LEFT JOIN (
                SELECT id_ctsp, SUM(so_luong) AS so_luong
                FROM size_ctsp
                WHERE id_ctsp =:idctsp
                GROUP BY id_ctsp
            ) sct ON spct.id = sct.id_ctsp
            WHERE spct.id =:idctsp
            """,nativeQuery = true)
    Integer getSLTonTongByIDCT(@Param("idctsp") Integer idctsp);


    @Query(value = """
        select sizeCT.id , s.ten as ten,s.mo_ta as moTa,  msct.so_luong as soLuong
          from san_pham_chi_tiet spct
         join mau_sac_ctsp msct on msct.id_ctsp = spct.id
         join mau_sac ms on ms.id = msct.id_mau_sac
         join size_ctsp sizeCT on sizeCT.id = msct.id_size_ctsp
         join size s on s.id = sizeCT.id_size  where spct.id =:idctsp and msct.id_mau_sac =:idms
         group by sizeCT.id,  s.ten , msct.so_luong
         having msct.so_luong > 0
        """,nativeQuery = true)
    List<SizeResponse> getListSizeByMauSac(Integer idctsp,Integer idms);


    @Query(value = """
        select msct.id, ms.id as idMS, ms.ten as ten,msct.anh as anh,msct.mo_ta as moTa,
         msct.so_luong as soLuong
         from san_pham_chi_tiet spct
        join san_pham sp on spct.id_san_pham = sp.id
        join mau_sac_ctsp msct on msct.id_ctsp = spct.id
        join mau_sac ms on ms.id = msct.id_mau_sac
        join size_ctsp sizeCT on sizeCT.id = msct.id_size_ctsp
        join size s on s.id = sizeCT.id_size  where spct.id =:idctsp and msct.id_size_ctsp =:idsizect
        group by msct.id,  ms.ten , msct.mo_ta,msct.so_luong
        having msct.so_luong > 0;
        """,nativeQuery = true)

    List<MauSacResponse> getListMauSacBySize(Integer idctsp,Integer idsizect);


    @Query(value = """
            select ms.id as IdMS,sizeCT.id as IdSize, msct.anh as anh, sp.ten as tenSP,
            spct.gia_ban as giaBan, spct.gia_sau_giam as giaSauGiam,
             msct.so_luong as SLMS, s.ten as tenSize
             from san_pham_chi_tiet spct
            join san_pham sp on spct.id_san_pham = sp.id
            join mau_sac_ctsp msct on msct.id_ctsp = spct.id
            join mau_sac ms on ms.id = msct.id_mau_sac
            join size_ctsp sizeCT on sizeCT.id = msct.id_size_ctsp
            join size s on s.id = sizeCT.id_size
            where spct.id =:idctsp and  msct.id_size_ctsp =:idsizect and msct.id_mau_sac =:idms
            group by msct.id ,msct.so_luong, s.ten,sizeCT.id
            having msct.so_luong > 0
            """,nativeQuery = true)
    SelectedSanPhamResponse getSanPhamSelected(Integer idctsp, Integer idms, Integer idsizect);

}
