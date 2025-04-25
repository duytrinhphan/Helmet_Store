package com.example.demo.reponsitory;

import com.example.demo.entity.TrongLuong;
import com.example.demo.entity.VatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VatLieuReponsitory extends JpaRepository<VatLieu, Integer> {
    @Query("select pot from  VatLieu pot where pot.ten like :keyword or pot.ma like :keyword")
    Page<VatLieu> search(String keyword, Pageable pageable);
    List<VatLieu> findAllByTrangThai(Integer trangThai, Sort sort);
}
