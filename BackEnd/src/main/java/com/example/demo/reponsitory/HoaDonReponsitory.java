package com.example.demo.reponsitory;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonReponsitory extends JpaRepository<HoaDon, Integer> {
}
