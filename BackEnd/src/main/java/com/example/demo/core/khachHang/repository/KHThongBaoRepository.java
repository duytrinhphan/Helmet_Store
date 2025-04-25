package com.example.demo.core.khachHang.repository;

import com.example.demo.entity.ThongBao;
import com.example.demo.reponsitory.ThongBaoRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KHThongBaoRepository extends ThongBaoRepository {
    @Query("select  pot from  ThongBao  pot where (pot.type =1 or  pot.type =2 or  pot.type =4 or  pot.type =5 or  pot.type =7 or  pot.type =8) and pot.user.id=:id")
    List<ThongBao> findAll(Integer id);

    @Query(value = """
            SELECT COUNT(pot.id) AS total  FROM thong_bao pot WHERE (pot.type =1 or  pot.type =2 or  pot.type =4 or  pot.type =5 or  pot.type =7 or  pot.type =8) and pot.trang_thai=1 and pot.id_user=:id
            """,nativeQuery = true)
    Integer dem(Integer id);
}
