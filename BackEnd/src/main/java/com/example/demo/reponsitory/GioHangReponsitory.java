package com.example.demo.reponsitory;

import com.example.demo.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangReponsitory extends JpaRepository<GioHang, Integer> {
}
