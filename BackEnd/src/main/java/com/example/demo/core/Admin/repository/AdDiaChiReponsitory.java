package com.example.demo.core.Admin.repository;

import com.example.demo.entity.DiaChi;
import com.example.demo.reponsitory.DiaChiReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdDiaChiReponsitory extends DiaChiReponsitory {

    @Query("select u from DiaChi u where u.user.id =:id")
    List<DiaChi> findDiaChiByIdUser(Integer id);
}
