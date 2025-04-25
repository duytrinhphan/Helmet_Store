package com.example.demo.reponsitory;

import com.example.demo.entity.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhuyenMaiReponsitory extends JpaRepository<KhuyenMai,Integer> {
}
