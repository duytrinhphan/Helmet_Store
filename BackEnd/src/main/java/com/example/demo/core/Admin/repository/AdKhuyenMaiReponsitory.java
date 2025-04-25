package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminKhuyenMaiResponse;
import com.example.demo.entity.KhuyenMai;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.reponsitory.KhuyenMaiReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdKhuyenMaiReponsitory extends KhuyenMaiReponsitory {

    KhuyenMai getOneById(Integer id);

    // lấy danh sách khuyến mại ngoại trừ những khuyến mại có trạng thái là đã xoá
    @Query("select i from KhuyenMai  i where i.trangThai <> 4 order by  i.id desc ")
    List<AdminKhuyenMaiResponse> getKhuyenMaiByTrangThai();

    @Query("SELECT i FROM KhuyenMai i WHERE i.thoiGianKetThuc <= CURRENT_DATE")
    List<KhuyenMai> findKhuyenMaiByHetHan();

    @Query("SELECT i FROM KhuyenMai i WHERE i.thoiGianBatDau >= CURRENT_DATE")
    List<KhuyenMai> findKhuyenMaiByChuaBatDau();

    @Query("SELECT i FROM KhuyenMai i WHERE i.thoiGianBatDau <= CURRENT_DATE and i.thoiGianKetThuc>= CURRENT_DATE ")
    List<KhuyenMai> findKhuyenMaiByConHan();


    // lấy danh sách ctsp có idkm = null hoặc trạng thái khuyến mại không phải là đang diễn ra hoặc chưa bắt đầu
    @Query("SELECT c FROM SanPhamChiTiet c LEFT JOIN c.khuyenMai km WHERE (c.khuyenMai IS NULL) OR (c.khuyenMai IS NOT NULL AND km.trangThai not in (0,2))")
    List<SanPhamChiTiet> getAllCTSPByKhuyenMai();

    // lấy danh sách SPCT theo trạng thái khuyến mại
    @Query("select  pt  from  SanPhamChiTiet  pt where pt.khuyenMai.trangThai =:trangThai")
    List<SanPhamChiTiet> getCTSPByTrangThaiKhuyenMai(Integer trangThai);

    @Query("SELECT i FROM KhuyenMai i WHERE i.ten=:ten")
    KhuyenMai findKhuyenMaiByTen(String ten);
}
