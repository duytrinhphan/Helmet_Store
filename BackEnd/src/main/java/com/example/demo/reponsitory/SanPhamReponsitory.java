package com.example.demo.reponsitory;

import com.example.demo.entity.Loai;
import com.example.demo.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamReponsitory extends JpaRepository<SanPham, Integer> {

}
