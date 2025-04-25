package com.example.demo.reponsitory;

import com.example.demo.entity.ThongBao;
import com.example.demo.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Integer> {
}
