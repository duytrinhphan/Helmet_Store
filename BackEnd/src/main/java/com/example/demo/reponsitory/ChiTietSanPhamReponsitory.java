package com.example.demo.reponsitory;

import com.example.demo.entity.Loai;
import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietSanPhamReponsitory extends JpaRepository<SanPhamChiTiet, Integer> {

    @Query("select spct from SanPhamChiTiet spct where spct.sanPham.loai.ten = :tenLoai and spct.trangThai = 1")
    Page<SanPhamChiTiet> findByLoai(@Param("tenLoai") String tenLoai, Pageable pageable);

    Page<SanPhamChiTiet> findAllByTrangThai(Integer trangThai,Pageable pageable);

}
