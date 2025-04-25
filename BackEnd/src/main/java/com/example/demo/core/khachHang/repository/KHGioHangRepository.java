package com.example.demo.core.khachHang.repository;

import com.example.demo.entity.GioHang;
import com.example.demo.reponsitory.GioHangReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KHGioHangRepository extends GioHangReponsitory {

    @Query("select pt from GioHang  pt where pt.user.id =:idKH")
    GioHang finbyIdKH(Integer idKH);
}
