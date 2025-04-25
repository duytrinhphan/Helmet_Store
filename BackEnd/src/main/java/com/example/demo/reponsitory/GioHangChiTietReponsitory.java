package com.example.demo.reponsitory;

import com.example.demo.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangChiTietReponsitory extends JpaRepository<GioHangChiTiet, Integer> {
}
