package com.example.demo.core.Admin.repository;

import com.example.demo.entity.MauSac;
import com.example.demo.entity.Size;
import com.example.demo.reponsitory.SizeReponsitory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdSizeReponsitory extends SizeReponsitory {

    List<Size> getAllByTrangThai(Integer trangThai, Sort sort);

    @Query("select  pot from  Size  pot " +
            "where pot.ten like :keyword ")
    Size findByTenSizeExcel(String keyword);

}
