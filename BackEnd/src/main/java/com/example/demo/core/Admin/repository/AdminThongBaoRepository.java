package com.example.demo.core.Admin.repository;

import com.example.demo.entity.Loai;
import com.example.demo.entity.ThongBao;
import com.example.demo.reponsitory.ThongBaoRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminThongBaoRepository extends ThongBaoRepository {

    @Query("select  pot from  ThongBao  pot where pot.type =0 or  pot.type =3 or  pot.type =6")
    List<ThongBao> findAll();

    @Query(value = """
            SELECT COUNT(pot.id) AS total  FROM thong_bao pot WHERE (pot.type = 0 OR pot.type = 3 OR pot.type = 6) and pot.trang_thai=1;
            """,nativeQuery = true)
    Integer dem();
}
