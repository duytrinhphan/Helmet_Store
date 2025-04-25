package com.example.demo.reponsitory;

import com.example.demo.entity.ThuongHieu;
import com.example.demo.entity.TrongLuong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrongLuongRepository extends JpaRepository<TrongLuong, Integer> {
    @Query("select pot from  TrongLuong pot where pot.donVi like :keyword or pot.ma like :keyword")
    Page<TrongLuong> search(String keyword, Pageable pageable);
    List<TrongLuong> findAllByTrangThai(Integer trangThai, Sort sort);
}
